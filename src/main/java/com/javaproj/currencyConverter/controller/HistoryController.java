package com.javaproj.currencyConverter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaproj.currencyConverter.model.ConversionHistory;
import com.javaproj.currencyConverter.service.HistoryService;

@RestController
@RequestMapping("/api/currency")
public class HistoryController {

	private final HistoryService historyService;

	public HistoryController(HistoryService historyService) {
		this.historyService = historyService;
	}

	@GetMapping("/history")
	public List<ConversionHistory> getHistory() {
		return historyService.getAllHistory();
	}

	@GetMapping("/history/{id}")
	public ConversionHistory getHistoryById(@PathVariable Long id) {

		return historyService.getHistoryById(id);
	}

	@DeleteMapping("/history/{id}")
	public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {

		historyService.deleteHistory(id);

		return ResponseEntity.noContent().build();
	}

}