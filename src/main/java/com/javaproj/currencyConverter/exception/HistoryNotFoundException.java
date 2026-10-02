package com.javaproj.currencyConverter.exception;

public class HistoryNotFoundException extends RuntimeException {

    public HistoryNotFoundException(Long id) {
        super("History record not found: " + id);
    }
}