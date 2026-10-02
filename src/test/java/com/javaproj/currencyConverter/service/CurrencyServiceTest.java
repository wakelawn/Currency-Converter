package com.javaproj.currencyConverter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.javaproj.currencyConverter.dto.ConversionRequest;
import com.javaproj.currencyConverter.dto.ConversionResponse;
import com.javaproj.currencyConverter.dto.ExchangeRateResponse;
import com.javaproj.currencyConverter.model.ConversionHistory;
import com.javaproj.currencyConverter.repository.ConversionHistoryRepository;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private ConversionHistoryRepository repository;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void testConversionResponseObject() {

        ConversionResponse response = new ConversionResponse(
                "USD",
                "INR",
                new BigDecimal("100"),
                new BigDecimal("83.50"),
                new BigDecimal("8350")
        );

        assertNotNull(response);
        assertEquals("USD", response.getFrom());
        assertEquals("INR", response.getTo());
        assertEquals(
                new BigDecimal("100"),
                response.getAmount()
        );
        assertEquals(
                new BigDecimal("83.50"),
                response.getRate()
        );
        assertEquals(
                new BigDecimal("8350"),
                response.getConvertedAmount()
        );
    }

    @Test
    void testExchangeRateResponse() {

        ExchangeRateResponse response = new ExchangeRateResponse();

        response.setResult("success");
        response.setBaseCode("USD");
        response.setConversionRates(
                Map.of(
                        "INR", 83.50,
                        "EUR", 0.92
                )
        );

        assertEquals("success", response.getResult());
        assertEquals("USD", response.getBaseCode());
        assertEquals(
                83.50,
                response.getConversionRates().get("INR")
        );
        assertEquals(
                0.92,
                response.getConversionRates().get("EUR")
        );
    }

    @Test
    void testConversionRequest() {

        ConversionRequest request = new ConversionRequest();

        request.setFrom("USD");
        request.setTo("INR");
        request.setAmount(new BigDecimal("100"));

        assertEquals("USD", request.getFrom());
        assertEquals("INR", request.getTo());
        assertEquals(
                new BigDecimal("100"),
                request.getAmount()
        );
    }

    @Test
    @SuppressWarnings("rawtypes")
    void convert_shouldCalculateAmountAndSaveHistory() {

        ConversionRequest request = new ConversionRequest();
        request.setFrom("USD");
        request.setTo("INR");
        request.setAmount(new BigDecimal("100"));

        ExchangeRateResponse exchangeResponse =
                new ExchangeRateResponse();

        exchangeResponse.setResult("success");
        exchangeResponse.setBaseCode("USD");
        exchangeResponse.setConversionRates(
                Map.of("INR", 83.50)
        );

        WebClient.RequestHeadersUriSpec requestHeadersUriSpec =
                mock(WebClient.RequestHeadersUriSpec.class);

        WebClient.RequestHeadersSpec requestHeadersSpec =
                mock(WebClient.RequestHeadersSpec.class);

        WebClient.ResponseSpec responseSpec =
                mock(WebClient.ResponseSpec.class);

        when(webClient.get())
                .thenReturn(requestHeadersUriSpec);

        when(requestHeadersUriSpec.uri(anyString()))
                .thenReturn(requestHeadersSpec);

        when(requestHeadersSpec.retrieve())
                .thenReturn(responseSpec);

        when(responseSpec.onStatus(any(), any()))
                .thenReturn(responseSpec);

        when(responseSpec.bodyToMono(ExchangeRateResponse.class))
                .thenReturn(
                        reactor.core.publisher.Mono.just(exchangeResponse)
                );

        ConversionResponse result =
                currencyService.convert(request);

        assertEquals("USD", result.getFrom());
        assertEquals("INR", result.getTo());
        assertEquals(
                new BigDecimal("100"),
                result.getAmount()
        );
        assertEquals(
                new BigDecimal("83.5"),
                result.getRate()
        );
        assertEquals(
                new BigDecimal("8350.0"),
                result.getConvertedAmount()
        );

        verify(repository)
                .save(any(ConversionHistory.class));
    }
}