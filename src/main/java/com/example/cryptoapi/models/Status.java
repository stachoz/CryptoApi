package com.example.cryptoapi.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(precision = 126, scale = 15)
    private BigDecimal currentAmount;
    @NotNull
    @Column(precision = 126, scale = 15)
    private BigDecimal currentProfit;

    @NotNull
    @Column(precision = 126, scale = 15)
    private BigDecimal historicalCoinPrice;
    @ManyToOne
    private Coin coin;

    public BigDecimal getHistoricalCoinPrice() {
        return historicalCoinPrice;
    }

    public void setHistoricalCoinPrice(BigDecimal historicalCoinPrice) {
        this.historicalCoinPrice = historicalCoinPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public BigDecimal getCurrentProfit() {
        return currentProfit;
    }

    public void setCurrentProfit(BigDecimal currentProfit) {
        this.currentProfit = currentProfit;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }
}
