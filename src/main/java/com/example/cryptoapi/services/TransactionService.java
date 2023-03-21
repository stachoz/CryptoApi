package com.example.cryptoapi.services;

import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.dtos.transaction.TransactionDtoMapper;
import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.models.Status;
import com.example.cryptoapi.models.Transaction;
import com.example.cryptoapi.repos.CoinRepository;
import com.example.cryptoapi.repos.StatusRepository;
import com.example.cryptoapi.repos.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDtoMapper transactionDtoMapper;
    private final StatusRepository statusRepository;
    private final CoinRepository coinRepository;


    public TransactionService(TransactionRepository transactionRepository, TransactionDtoMapper transactionDtoMapper, StatusRepository statusRepository,
                              CoinRepository coinRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionDtoMapper = transactionDtoMapper;
        this.statusRepository = statusRepository;
        this.coinRepository = coinRepository;
    }
    public List<TransactionDto> getAllTransactions(){
        List<TransactionDto> transactions = new ArrayList<>();
        transactionRepository.findAll().forEach(
                t -> transactions.add(transactionDtoMapper.map(t)));
        return transactions;
    }

    public TransactionDto saveTransaction(TransactionDto dto){
        Transaction savedTransaction = transactionRepository.save(transactionDtoMapper.map(dto));
        return transactionDtoMapper.map(savedTransaction);
    }

   private void updateStatus(TransactionDto dto){
        Long coinId = dto.getCoinId();
        Optional<Status> lastStatus = statusRepository.findTopByCoin_IdOrderByIdDesc(coinId);
        Coin coin = coinRepository.findById(coinId).orElseThrow(NoSuchElementException::new);
        if(!lastStatus.isPresent()){
            Status status = new Status();
            status.setId(coinId);

        }
   }
}
