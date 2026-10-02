package com.javaproj.currencyConverter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javaproj.currencyConverter.model.ConversionHistory;
import com.javaproj.currencyConverter.repository.ConversionHistoryRepository;
import com.javaproj.currencyConverter.exception.HistoryNotFoundException;

@Service
public class HistoryService {

	private final ConversionHistoryRepository conversionHistoryRepository;

	public HistoryService(ConversionHistoryRepository conversionHistoryRepository) {

		this.conversionHistoryRepository = conversionHistoryRepository;
	}

	public List<ConversionHistory> getAllHistory() {
		return conversionHistoryRepository.findAll();
	}

	public ConversionHistory getHistoryById(Long id) {

		return conversionHistoryRepository.findById(id).orElseThrow(() -> new HistoryNotFoundException(id));
	}

	public void deleteHistory(Long id) {

		if (!conversionHistoryRepository.existsById(id)) {
			throw new HistoryNotFoundException(id);
		}

		conversionHistoryRepository.deleteById(id);
	}
}