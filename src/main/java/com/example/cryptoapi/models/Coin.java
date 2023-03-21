package com.example.cryptoapi.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="coin_name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @Column(name="symbol", unique = true, nullable = false)
    @NotBlank
    private String symbol;
    @OneToMany(mappedBy = "coin")
    private List<Transaction> transactions = new ArrayList<>();
    @OneToMany(mappedBy = "coin")
    private List<Status> statuses = new ArrayList<>();

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
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
