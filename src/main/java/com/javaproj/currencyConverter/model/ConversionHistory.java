package com.javaproj.currencyConverter.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal amount;
    private BigDecimal rate;
    private BigDecimal convertedAmount;

    private LocalDateTime createdAt;

    public ConversionHistory() {
    }

    public ConversionHistory(
            String fromCurrency,
            String toCurrency,
            BigDecimal amount,
            BigDecimal rate,
            BigDecimal convertedAmount,
            LocalDateTime createdAt) {

        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.rate = rate;
        this.convertedAmount = convertedAmount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}