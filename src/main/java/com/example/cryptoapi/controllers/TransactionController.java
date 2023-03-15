package com.example.cryptoapi.controllers;

import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
        TransactionDto saveTransaction = transactionService.saveTransaction(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveTransaction.getTransactionId())
                .toUri();
        return ResponseEntity.created(uri).body(saveTransaction);
    }




}
