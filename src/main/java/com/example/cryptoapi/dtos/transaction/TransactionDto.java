package com.example.cryptoapi.dtos.transaction;

import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.models.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {
    private Long transactionId;
    private BigDecimal amount;
    private BigDecimal prize;
    private Date date;
    private TransactionType type;
    private Long coinId;

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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
