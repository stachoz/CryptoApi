package com.example.cryptoapi.dtos.transaction;

import com.example.cryptoapi.models.Transaction;

public class TransactionDtoMapper {
    public static TransactionDto map(Transaction transaction){
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setPrize(transaction.getPrize());
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getType());
        dto.setCoin(transaction.getCoin());
        return dto;
    }

    public static Transaction map(TransactionDto dto){
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setPrize(dto.getPrize());
        transaction.setDate(dto.getDate());
        transaction.setType(dto.getType());
        transaction.setCoin(dto.getCoin());
        return transaction;
    }
}
