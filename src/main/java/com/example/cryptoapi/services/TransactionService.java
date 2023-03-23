package com.example.cryptoapi.services;

import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.dtos.transaction.TransactionDtoMapper;
import com.example.cryptoapi.models.Transaction;
import com.example.cryptoapi.repos.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDtoMapper transactionDtoMapper;
    private final StatusService statusService;

    public TransactionService(TransactionRepository transactionRepository, TransactionDtoMapper transactionDtoMapper, StatusService statusService) {
        this.transactionRepository = transactionRepository;
        this.transactionDtoMapper = transactionDtoMapper;
        this.statusService = statusService;
    }
    public List<TransactionDto> getAllTransactions(){
        List<TransactionDto> transactions = new ArrayList<>();
        transactionRepository.findAll().forEach(
                t -> transactions.add(transactionDtoMapper.map(t)));
        return transactions;
    }
    @Transactional
    public TransactionDto saveTransaction(TransactionDto dto){
        Transaction savedTransaction = transactionRepository.save(transactionDtoMapper.map(dto));
        statusService.updateStatus(dto);
        return transactionDtoMapper.map(savedTransaction);
    }
}
