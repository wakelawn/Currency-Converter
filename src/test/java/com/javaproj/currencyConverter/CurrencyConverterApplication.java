package com.javaproj.currencyConverter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    classes = CurrenyConverterApplication.class,
    properties = {
        "exchange.api.key=test-api-key"
    }
)
class CurrencyConverterApplicationTests {

    @Test
    void contextLoads() {
    }
}