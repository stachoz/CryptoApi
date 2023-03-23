package com.example.cryptoapi.services;

import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.models.Status;
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

    public void updateStatus(TransactionDto dto){
        Long coinId = dto.getCoinId();
        Optional<Status> lastStatus = statusRepository.findTopByCoin_IdOrderByIdDesc(coinId);
        Coin coin = coinRepository.findById(coinId).orElseThrow(NoSuchElementException::new);
        Status status = new Status();
        if(!lastStatus.isPresent()){
            BigDecimal amount = dto.getAmount();
            BigDecimal price = dto.getPrice();
            String coinSymbol = coin.getSymbol();
            status.setCurrentAmount(amount);
            status.setCurrentProfit(countProfit(coinSymbol, amount, price));
            status.setCoin(coin);
        }
        statusRepository.save(status);
    }

    private BigDecimal countProfit(String coinSymbol, BigDecimal amount, BigDecimal price) {
        JSONObject coinJson = coinApiService.getExchangeRate(coinSymbol).orElseThrow(NoSuchElementException::new);
        Double rate = (Double) coinJson.get("rate");
        BigDecimal currentCoinPrice = BigDecimal.valueOf(rate);
        BigDecimal oldCoinRatio = amount.multiply(price);
        BigDecimal currentCoinRatio = amount.multiply(currentCoinPrice);
        BigDecimal subtract = currentCoinRatio.subtract(oldCoinRatio);
        return subtract;
    }
}
