package com.tot.system.service;

import com.tot.system.model.History;
import com.tot.system.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class HistoryServiceTest {
    @MockBean
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryService historyService;

    @Test
    void shouldSaveHistory() {
        History expected = new History();
        when(historyRepository.save(expected)).thenReturn(expected);
        assertEquals(expected, historyService.save(expected));
    }

    @Test
    void shouldFindAllHistories() {
        List<History> expected = new ArrayList<>();
        when(historyRepository.findAll()).thenReturn(expected);
        assertEquals(expected, historyService.findAll());
    }

    @Test
    void shouldFindHistoryById() {
        Optional<History> expected = Optional.of(new History());
        when(historyRepository.findById(21l)).thenReturn(expected);
        assertEquals(expected, historyService.findById(21l));
    }

    @Test
    void shouldDeleteHistoryById() {
        doNothing().when(historyRepository).deleteById(21l);
        historyService.deleteById(21l);
        verify(historyRepository, times(1)).deleteById(21l);
    }
}