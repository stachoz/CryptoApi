package com.example.cryptoapi.dtos.transaction;

import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.models.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {
    private Long id;
    private BigDecimal amount;
    private BigDecimal prize;
    private Date date;
    private TransactionType type;
    private Coin coin;

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrize() {
        return prize;
    }

    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
