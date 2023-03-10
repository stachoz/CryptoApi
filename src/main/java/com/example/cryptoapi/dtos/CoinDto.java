package com.example.cryptoapi.dtos;

import javax.validation.constraints.NotBlank;

public class CoinDto {
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String symbol;

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
