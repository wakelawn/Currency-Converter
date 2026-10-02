package com.javaproj.currencyConverter.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.javaproj.currencyConverter.dto.ConversionRequest;
import com.javaproj.currencyConverter.dto.ConversionResponse;
import com.javaproj.currencyConverter.dto.ExchangeRateResponse;
import com.javaproj.currencyConverter.exception.CurrencyNotFoundException;

import java.time.LocalDateTime;

import com.javaproj.currencyConverter.model.ConversionHistory;
import com.javaproj.currencyConverter.repository.ConversionHistoryRepository;

@Service
public class CurrencyService {

	private final WebClient webClient;
	private final ConversionHistoryRepository conversionHistoryRepository;

	@Value("${exchange.api.url}")
	private String apiUrl;

	@Value("${exchange.api.key}")
	private String apiKey;

	public CurrencyService(WebClient webClient, ConversionHistoryRepository conversionHistoryRepository) {

		this.webClient = webClient;
		this.conversionHistoryRepository = conversionHistoryRepository;
	}

	public ConversionResponse convert(ConversionRequest request) {

		String from = request.getFrom().toUpperCase();
		String to = request.getTo().toUpperCase();

		ExchangeRateResponse response = webClient.get().uri(apiUrl + "/" + apiKey + "/latest/" + from).retrieve()
				.onStatus(status -> status.is4xxClientError(),
						clientResponse -> reactor.core.publisher.Mono.error(new CurrencyNotFoundException(from)))
				.onStatus(status -> status.is5xxServerError(),
						clientResponse -> reactor.core.publisher.Mono
								.error(new RuntimeException("Currency exchange service is unavailable")))
				.bodyToMono(ExchangeRateResponse.class).block();

		Double rateValue = response.getConversionRates().get(to);

		if (rateValue == null) {
			throw new CurrencyNotFoundException(to);
		}

		BigDecimal rate = BigDecimal.valueOf(rateValue);

		BigDecimal convertedAmount = request.getAmount().multiply(rate);

		ConversionHistory history = new ConversionHistory(from, to, request.getAmount(), rate, convertedAmount,
				LocalDateTime.now());

		conversionHistoryRepository.save(history);

		return new ConversionResponse(from, to, request.getAmount(), rate, convertedAmount);
	}

	public ExchangeRateResponse getRates(String baseCurrency) {

		String base = baseCurrency.toUpperCase();

		ExchangeRateResponse response = webClient.get().uri(apiUrl + "/" + apiKey + "/latest/" + base).retrieve()
				.onStatus(status -> status.is4xxClientError(),
						clientResponse -> reactor.core.publisher.Mono.error(new CurrencyNotFoundException(base)))
				.bodyToMono(ExchangeRateResponse.class).block();

		if (response == null || !"success".equals(response.getResult())) {
			throw new CurrencyNotFoundException(base);
		}

		return response;
	}
}