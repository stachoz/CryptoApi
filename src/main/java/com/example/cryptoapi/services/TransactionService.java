package com.example.cryptoapi.services;

import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.dtos.transaction.TransactionDtoMapper;
import com.example.cryptoapi.models.Transaction;
import com.example.cryptoapi.models.TransactionType;
import com.example.cryptoapi.repos.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<TransactionDto> saveTransaction(TransactionDto dto){
        BigDecimal transactionAmount = dto.getAmount();
        Long coinId = dto.getCoinId();
        if(dto.getType() == TransactionType.SELL && !isSellCoinAmountValid(transactionAmount, coinId)){
            return Optional.empty();
        }
        Transaction savedTransaction = transactionRepository.save(transactionDtoMapper.map(dto));
        statusService.updateStatus(dto);
        return Optional.of(transactionDtoMapper.map(savedTransaction));
    }

    private boolean isSellCoinAmountValid(BigDecimal transactionCoinAmount, Long coinId){
        BigDecimal currentCoinAmount = statusService.getCurrentCoinAmountById(coinId);
        return transactionCoinAmount.compareTo(currentCoinAmount) <= 0;
    }
}
