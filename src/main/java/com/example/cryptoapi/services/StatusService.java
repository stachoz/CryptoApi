package com.example.cryptoapi.services;

import com.example.cryptoapi.dtos.status.StatusDto;
import com.example.cryptoapi.dtos.status.StatusDtoMapper;
import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.models.Status;
import com.example.cryptoapi.models.Transaction;
import com.example.cryptoapi.models.TransactionType;
import com.example.cryptoapi.repos.CoinRepository;
import com.example.cryptoapi.repos.StatusRepository;
import com.example.cryptoapi.repos.TransactionRepository;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Optional<StatusDto> getStatusByCoinId(Long id){
        Optional<StatusDto> statusDto = statusRepository.findTopByCoin_IdOrderByIdDesc(id)
                .map(statusDtoMapper::map);
        if (statusDto.isPresent()){
            updateStatus(id);
        }
        return statusDto;   
    }

    public BigDecimal getCurrentCoinAmountById(Long coinId){
        return statusRepository.findTopByCoin_IdOrderByIdDesc(coinId)
                .map(Status::getCurrentAmount)
                .orElse(BigDecimal.ZERO);
    }
    public void updateStatus(Long coinId){
        Coin coin = coinRepository.findById(coinId).orElseThrow(NoSuchElementException::new);
        String coinSymbol = coin.getSymbol();
        List<Transaction> transactions = transactionRepository.findAllByCoin_Id(coinId).orElseThrow(NoSuchElementException::new);
        Status status = new Status();

        BigDecimal currentCoinPrice = getCurrentCoinPrice(coinSymbol);

        BigDecimal profit = BigDecimal.valueOf(0);
        BigDecimal amount = BigDecimal.valueOf(0);
        BigDecimal value = BigDecimal.valueOf(0);
        for (Transaction transaction : transactions) {
            BigDecimal transactionAmount = transaction.getAmount();
            BigDecimal transactionPrice = transaction.getPrice();
            BigDecimal profitFromOneTransaction = countProfit(transactionAmount, transactionPrice, currentCoinPrice);
            if(transaction.getType() == TransactionType.BUY){
                profit = profit.add(profitFromOneTransaction);
                amount =  amount.add(transactionAmount);
                value = value.add(transactionAmount.multiply(transactionPrice));
            } else {
                profit = profit.subtract(profitFromOneTransaction);
                amount = amount.subtract(transactionAmount);
                value = value.subtract(transactionAmount.multiply(transactionPrice));
            }
        }
        status.setCoin(coin);
        status.setHistoricalCoinPrice(currentCoinPrice);
        status.setCurrentAmount(amount);
        status.setCurrentProfit(profit);
        status.setTotalCurrencyValue(value);
        statusRepository.save(status);
    }

    /*
        negative value possible
    */
    private BigDecimal countProfit(BigDecimal amount, BigDecimal transactionPrice, BigDecimal currentPrice) {
        BigDecimal transactionRatio = amount.multiply(transactionPrice);
        BigDecimal currentRatio = amount.multiply(currentPrice);
        return currentRatio.subtract(transactionRatio);
    }

    public BigDecimal getCurrentCoinPrice(String coinSymbol){
        JSONObject coinJson = coinApiService.getCoinJSON(coinSymbol).orElseThrow(NoSuchElementException::new);
        Double rate = (Double) coinJson.get("rate");
        return BigDecimal.valueOf(rate);
    }
}
