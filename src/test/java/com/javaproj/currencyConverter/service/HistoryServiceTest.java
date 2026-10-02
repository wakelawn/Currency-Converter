package com.javaproj.currencyConverter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javaproj.currencyConverter.exception.HistoryNotFoundException;
import com.javaproj.currencyConverter.model.ConversionHistory;
import com.javaproj.currencyConverter.repository.ConversionHistoryRepository;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    @Mock
    private ConversionHistoryRepository repository;

    @InjectMocks
    private HistoryService historyService;

    private ConversionHistory history;

    @BeforeEach
    void setUp() {
        history = mock(ConversionHistory.class);
    }

    @Test
    void getHistoryById_shouldReturnHistory_whenIdExists() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(history));

        ConversionHistory result =
                historyService.getHistoryById(1L);

        assertEquals(history, result);

        verify(repository).findById(1L);
    }

    @Test
    void getHistoryById_shouldThrowException_whenIdDoesNotExist() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                HistoryNotFoundException.class,
                () -> historyService.getHistoryById(1L)
        );

        verify(repository).findById(1L);
    }

    @Test
    void deleteHistory_shouldDelete_whenIdExists() {

        when(repository.existsById(1L))
                .thenReturn(true);

        historyService.deleteHistory(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deleteHistory_shouldThrowException_whenIdDoesNotExist() {

        when(repository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                HistoryNotFoundException.class,
                () -> historyService.deleteHistory(1L)
        );

        verify(repository, never()).deleteById(1L);
    }
}