package com.example.cryptoapi.controllers;

import com.example.cryptoapi.dtos.transaction.TransactionDto;
import com.example.cryptoapi.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController("transactions/")
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
                .buildAndExpand(saveTransaction.getId())
                .toUri();
        return ResponseEntity.created(uri).body(saveTransaction);
    }




}
