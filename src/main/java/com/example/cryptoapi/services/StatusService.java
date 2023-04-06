package com.example.cryptoapi.services;

import com.example.cryptoapi.dtos.status.GeneralStatusDto;
import com.example.cryptoapi.dtos.status.StatusDto;
import com.example.cryptoapi.dtos.status.StatusDtoMapper;
import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.models.Status;
import com.example.cryptoapi.models.Transaction;
import com.example.cryptoapi.repos.CoinRepository;
import com.example.cryptoapi.repos.StatusRepository;
import com.example.cryptoapi.repos.TransactionRepository;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StatusService {
    private final CoinRepository coinRepository;
    private final StatusRepository statusRepository;
    private final CoinApiService coinApiService;
    private final TransactionRepository transactionRepository;
    private final StatusDtoMapper statusDtoMapper;

    public StatusService(CoinRepository coinRepository, StatusRepository statusRepository, CoinApiService coinApiService,
                         TransactionRepository transactionRepository, StatusDtoMapper statusDtoMapper) {
        this.coinRepository = coinRepository;
        this.statusRepository = statusRepository;
        this.coinApiService = coinApiService;
        this.transactionRepository = transactionRepository;
        this.statusDtoMapper = statusDtoMapper;
    }

    public GeneralStatusDto getGeneralStatus(){
        Iterable<Coin> all = coinRepository.findAll();
        BigDecimal totalValue = BigDecimal.ZERO;
        BigDecimal totalProfit = BigDecimal.ZERO;
        for (Coin coin : all) {
            StatusDto statusDto = countStatus(coin.getId());
            totalValue = totalValue.add(statusDto.getTotalCurrencyValue());
            totalProfit = totalProfit.add(statusDto.getCurrentProfit());
        }
        return new GeneralStatusDto(totalValue, totalProfit);
    }
    public BigDecimal getCurrentCoinAmountById(Long coinId){
        return statusRepository.findTopByCoin_IdOrderByIdDesc(coinId)
                .map(Status::getCurrentAmount)
                .orElse(BigDecimal.ZERO);
    }
    public StatusDto countStatus(Long coinId){
        Coin coin = coinRepository.findById(coinId).orElseThrow(NoSuchElementException::new);
        String coinSymbol = coin.getSymbol();
        List<Transaction> transactions = transactionRepository.findAllByCoin_Id(coinId).orElseThrow(NoSuchElementException::new);
        Status status = new Status();

        BigDecimal currentCoinPrice = getCurrentCoinPrice(coinSymbol);

        BigDecimal profit = BigDecimal.ZERO;
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal value = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            BigDecimal transactionAmount = transaction.getAmount();
            BigDecimal transactionPrice = transaction.getPrice();
            BigDecimal profitFromOneTransaction = countProfit(transactionAmount, transactionPrice, currentCoinPrice);
            profit = profit.add(profitFromOneTransaction);
            amount =  amount.add(transactionAmount);
            value = value.add(transactionAmount.multiply(transactionPrice));
        }
        status.setCoin(coin);
        status.setCurrentAmount(amount.stripTrailingZeros());
        status.setCurrentProfit(profit.stripTrailingZeros());
        status.setTotalCurrencyValue(value.stripTrailingZeros());
        return statusDtoMapper.map(statusRepository.save(status));
    }

    /*
        negative value possible
    */
    public BigDecimal getCurrentCoinPrice(String coinSymbol){
        JSONObject coinJson = coinApiService.getCoinJSON(coinSymbol).orElseThrow(NoSuchElementException::new);
        Double rate = (Double) coinJson.get("rate");
        return BigDecimal.valueOf(rate);
    }
    /*
        negative value possible
        currentRatio > transactionRatio (you can sell currency for higher price than you bought)
        currentRatio < transactionRatio (currency value is lower than it was in moment of transaction)
     */
    private BigDecimal countProfit(BigDecimal amount, BigDecimal transactionPrice, BigDecimal currentPrice) {
        BigDecimal transactionRatio = amount.multiply(transactionPrice);
        BigDecimal currentRatio = amount.multiply(currentPrice);
        return currentRatio.subtract(transactionRatio);
    }
}
