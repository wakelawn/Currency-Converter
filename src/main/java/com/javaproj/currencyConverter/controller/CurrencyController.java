package com.javaproj.currencyConverter.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.javaproj.currencyConverter.dto.ConversionRequest;
import com.javaproj.currencyConverter.dto.ConversionResponse;
import com.javaproj.currencyConverter.service.CurrencyService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javaproj.currencyConverter.dto.ExchangeRateResponse;

@RestController
public class CurrencyController {

	private final CurrencyService currencyService;

	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@GetMapping("/api/currency/rates/{baseCurrency}")
	public ExchangeRateResponse getRates(@PathVariable String baseCurrency) {

		return currencyService.getRates(baseCurrency);
	}

	@PostMapping("/api/currency/convert")
	public ConversionResponse convert(@Valid @RequestBody ConversionRequest request) {

		return currencyService.convert(request);
	}
}