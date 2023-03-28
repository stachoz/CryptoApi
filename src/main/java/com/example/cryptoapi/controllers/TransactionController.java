package com.example.cryptoapi.controllers;

import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.errors.ApiRequestException;
import com.example.cryptoapi.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("")
    ResponseEntity<List<TransactionDto>> getAllTransactions(){
        List<TransactionDto> allTransactions = transactionService.getAllTransactions();
        if(allTransactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allTransactions);
    }

    @PostMapping("")
    ResponseEntity<TransactionDto> saveTransaction(@RequestBody TransactionDto dto){
        Optional<TransactionDto> savedTransaction = transactionService.saveTransaction(dto);
        if(savedTransaction.isEmpty()) throw new ApiRequestException(
                "You are trying to sell more than you have. Chceck you coin amount", HttpStatus.BAD_REQUEST
        );
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTransaction.get().getTransactionId())
                .toUri();
        return ResponseEntity.created(uri).body(savedTransaction.get());
    }




}
