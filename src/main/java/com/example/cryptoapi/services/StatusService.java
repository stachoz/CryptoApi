package com.example.cryptoapi.services;

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

@Service
public class StatusService {
    private final CoinRepository coinRepository;
    private final StatusRepository statusRepository;
    private final CoinApiService coinApiService;
    private final TransactionRepository transactionRepository;

    public StatusService(CoinRepository coinRepository, StatusRepository statusRepository, CoinApiService coinApiService,
                         TransactionRepository transactionRepository) {
        this.coinRepository = coinRepository;
        this.statusRepository = statusRepository;
        this.coinApiService = coinApiService;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getCurrentCoinAmountById(Long coinId){
        return statusRepository.findTopByCoin_IdOrderByIdDesc(coinId)
                .map(Status::getCurrentAmount)
                .orElse(BigDecimal.ZERO);
    }
    public void updateStatus(TransactionDto transactionDto){
        Long coinId = transactionDto.getCoinId();
        Coin coin = coinRepository.findById(coinId).orElseThrow(NoSuchElementException::new);
        String coinSymbol = coin.getSymbol();
        List<Transaction> transactions = transactionRepository.findAllByCoin_Id(coinId).orElseThrow(NoSuchElementException::new);
        Status status = new Status();

        BigDecimal currentCoinPrice = getCurrentCoinPrice(coinSymbol);

        BigDecimal profit = BigDecimal.valueOf(0);
        BigDecimal amount = BigDecimal.valueOf(0);
        for (Transaction transaction : transactions) {
            BigDecimal transactionAmount = transaction.getAmount();
            BigDecimal transactionPrice = transaction.getPrice();
            BigDecimal profitFromOneTransaction = countProfit(transactionAmount, transactionPrice, currentCoinPrice);
            if(transaction.getType() == TransactionType.BUY){
                profit = profit.add(profitFromOneTransaction);
                amount =  amount.add(transactionAmount);
            } else {
                profit = profit.subtract(profitFromOneTransaction);
                amount = amount.subtract(transactionAmount);
            }
        }
        status.setCoin(coin);
        status.setHistoricalCoinPrice(currentCoinPrice);
        status.setCurrentAmount(amount);
        status.setCurrentProfit(profit);
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

    private BigDecimal getCurrentCoinPrice(String coinSymbol){
        JSONObject coinJson = coinApiService.getCoinJSON(coinSymbol).orElseThrow(NoSuchElementException::new);
        Double rate = (Double) coinJson.get("rate");
        return BigDecimal.valueOf(rate);
    }
}
