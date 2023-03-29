package com.example.cryptoapi.dtos.status;

import java.math.BigDecimal;

public class StatusDto {
    private Long id;
    private BigDecimal currentAmount;
    private BigDecimal currentProfit;
    private BigDecimal historicalCoinPrice;

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

    public BigDecimal getHistoricalCoinPrice() {
        return historicalCoinPrice;
    }

    public void setHistoricalCoinPrice(BigDecimal historicalCoinPrice) {
        this.historicalCoinPrice = historicalCoinPrice;
    }
}
