package com.javaproj.currencyConverter.dto;

import java.math.BigDecimal;

public class ConversionResponse {

    private String from;
    private String to;
    private BigDecimal amount;
    private BigDecimal rate;
    private BigDecimal convertedAmount;

    public ConversionResponse() {
    }

    public ConversionResponse(
            String from,
            String to,
            BigDecimal amount,
            BigDecimal rate,
            BigDecimal convertedAmount) {

        this.from = from;
        this.to = to;
        this.amount = amount;
        this.rate = rate;
        this.convertedAmount = convertedAmount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
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
}