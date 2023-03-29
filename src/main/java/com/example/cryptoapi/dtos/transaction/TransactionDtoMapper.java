package com.example.cryptoapi.dtos.transaction;

import com.example.cryptoapi.errors.ApiRequestException;
import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.models.Transaction;
import com.example.cryptoapi.repos.CoinRepository;
import com.example.cryptoapi.services.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionDtoMapper {
    private final CoinRepository coinRepository;
    private final StatusService statusService;

    public TransactionDtoMapper(CoinRepository coinRepository, StatusService statusService) {
        this.coinRepository = coinRepository;
        this.statusService = statusService;
    }

    public TransactionDto map(Transaction transaction){
        TransactionDto dto = new TransactionDto();
        dto.setTransactionId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setPrice(transaction.getPrice());
        dto.setType(transaction.getType());
        dto.setCoinId(transaction.getCoin().getId());
        return dto;
    }

    public Transaction map(TransactionDto dto){
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        Coin coin = coinRepository.findById(dto.getCoinId())
                .orElseThrow(() ->
                        new ApiRequestException(String.format("Coin with given id (%d) does not exists", dto.getCoinId()),
                                HttpStatus.BAD_REQUEST)
                );
        transaction.setCoin(coin);
        BigDecimal price = dto.getPrice();
        if(price == null){
            price = statusService.getCurrentCoinPrice(coin.getSymbol());
        }
        transaction.setPrice(price);
        transaction.setType(dto.getType());
        return transaction;
    }
}
