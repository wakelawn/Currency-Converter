package com.javaproj.currencyConverter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CurrenyConverterApplication.class, properties = {
		"exchange.api.key=test-api-key",
		"spring.datasource.url=jdbc:h2:mem:testdb",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.username=sa", "spring.datasource.password=",
		"spring.jpa.hibernate.ddl-auto=create-drop"})
class CurrencyConverterApplicationTests {

	@Test
	void contextLoads() {
	}
}