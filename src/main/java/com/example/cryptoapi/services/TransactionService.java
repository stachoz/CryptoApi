package com.example.cryptoapi.services;

import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.dtos.transaction.TransactionDtoMapper;
import com.example.cryptoapi.models.Transaction;
import com.example.cryptoapi.repos.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public List<TransactionDto> getAllTransactions(){
        List<TransactionDto> transactions = new ArrayList<>();
        transactionRepository.findAll().forEach(
                t -> transactions.add(TransactionDtoMapper.map(t))
        );
        return transactions;
    }

    public TransactionDto saveTransaction(TransactionDto dto){
        Transaction savedTransaction = transactionRepository.save(TransactionDtoMapper.map(dto));
        return TransactionDtoMapper.map(savedTransaction);
    }
}
