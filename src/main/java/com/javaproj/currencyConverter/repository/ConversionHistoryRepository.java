package com.javaproj.currencyConverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproj.currencyConverter.model.ConversionHistory;

public interface ConversionHistoryRepository
        extends JpaRepository<ConversionHistory, Long> {

}