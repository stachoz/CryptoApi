package com.example.cryptoapi.services;

import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.models.Status;
import com.example.cryptoapi.models.TransactionType;
import com.example.cryptoapi.repos.CoinRepository;
import com.example.cryptoapi.repos.StatusRepository;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StatusService {
    private final CoinRepository coinRepository;
    private final StatusRepository statusRepository;
    private final CoinApiService coinApiService;

    public StatusService(CoinRepository coinRepository, StatusRepository statusRepository, CoinApiService coinApiService) {
        this.coinRepository = coinRepository;
        this.statusRepository = statusRepository;
        this.coinApiService = coinApiService;
    }
    public void updateStatus(TransactionDto transactionDto){
        Status status = new Status();
        BigDecimal transactionCoinAmount = transactionDto.getAmount();
        BigDecimal transactionCoinPrice = transactionDto.getPrice();
        Long coinId = transactionDto.getCoinId();
        Optional<Status> lastStatus = statusRepository.findTopByCoin_IdOrderByIdDesc(coinId);
        Coin coin = coinRepository.findById(coinId).orElseThrow(NoSuchElementException::new);
        String coinSymbol = coin.getSymbol();

        BigDecimal currentCoinPrice = getCurrentCoinPrice(coinSymbol);
        BigDecimal profitFromTransaction = countProfit(coinSymbol, transactionCoinAmount, transactionCoinPrice, currentCoinPrice);

        if(!lastStatus.isPresent()) {
            status.setCurrentAmount(transactionCoinAmount);
            status.setCurrentProfit(profitFromTransaction);
            status.setHistoricalCoinPrice(currentCoinPrice);
            status.setCoin(coin);
        } 
        statusRepository.save(status);
    }

    private BigDecimal countProfit(String coinSymbol, BigDecimal amount, BigDecimal transactionPrice, BigDecimal currentPrice) {
        BigDecimal transactionRatio = amount.multiply(transactionPrice);
        BigDecimal currentRatio = amount.multiply(currentPrice);
        return  transactionRatio.subtract(currentRatio);
    }

    private BigDecimal getCurrentCoinPrice(String coinSymbol){
        JSONObject coinJson = coinApiService.getCoinJSON(coinSymbol).orElseThrow(NoSuchElementException::new);
        Double rate = (Double) coinJson.get("rate");
        return BigDecimal.valueOf(rate);
    }
}
