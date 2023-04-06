package com.example.cryptoapi.dtos.status;

import org.apache.el.lang.ELArithmetic;

import java.math.BigDecimal;

public class GeneralStatusDto {
    private BigDecimal totalValue;
    private BigDecimal totalProfit;

    public GeneralStatusDto(BigDecimal totalValue, BigDecimal totalProfit) {
        this.totalValue = totalValue;
        this.totalProfit = totalProfit;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }
}
