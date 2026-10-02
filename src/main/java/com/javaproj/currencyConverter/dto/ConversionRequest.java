package com.javaproj.currencyConverter.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ConversionRequest {

    @NotBlank(message = "From currency is required")
    private String from;

    @NotBlank(message = "To currency is required")
    private String to;

    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    public ConversionRequest() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}