package com.example.cryptoapi.dtos.coin;

import com.example.cryptoapi.models.Transaction;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class CoinDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Column(unique = true)
    private String symbol;
    @Nullable
    private List<Transaction> transactions= new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
