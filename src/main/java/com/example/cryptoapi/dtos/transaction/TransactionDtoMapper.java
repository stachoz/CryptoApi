package com.example.cryptoapi.dtos.transaction;

import com.example.cryptoapi.errors.ApiRequestException;
import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.models.Transaction;
import com.example.cryptoapi.repos.CoinRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TransactionDtoMapper {
    private final CoinRepository coinRepository;

    public TransactionDtoMapper(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public TransactionDto map(Transaction transaction){
        TransactionDto dto = new TransactionDto();
        dto.setTransactionId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setPrize(transaction.getPrize());
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getType());
        dto.setCoinId(transaction.getCoin().getId());
        return dto;
    }

    public Transaction map(TransactionDto dto){
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setPrize(dto.getPrize());
        transaction.setDate(dto.getDate());
        transaction.setType(dto.getType());
        Coin coin = coinRepository.findById(dto.getCoinId())
                .orElseThrow(() ->
                        new ApiRequestException(String.format("Coin with given id (%d) does not exists", dto.getCoinId()),
                        HttpStatus.BAD_REQUEST)
                );
        transaction.setCoin(coin);
        return transaction;
    }
}
