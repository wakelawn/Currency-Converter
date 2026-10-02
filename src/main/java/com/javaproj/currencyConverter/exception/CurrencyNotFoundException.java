package com.javaproj.currencyConverter.exception;

public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(String currency) {
        super("Currency not supported: " + currency);
    }
}