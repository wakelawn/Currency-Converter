package com.javaproj.currencyConverter.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CurrencyNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleCurrencyNotFound(CurrencyNotFoundException exception) {

		Map<String, String> error = new HashMap<>();

		error.put("error", exception.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {

		Map<String, String> errors = new HashMap<>();

		exception.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(HistoryNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleHistoryNotFound(HistoryNotFoundException exception) {

		Map<String, String> error = new HashMap<>();

		error.put("error", exception.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}