package com.tot.system.service;

import com.tot.system.model.History;
import com.tot.system.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Transactional
    public Optional<History> findById(Long id) {
        return historyRepository.findById(id);
    }

    @Transactional
    public List<History> findAll() {
        return historyRepository.findAll();
    }

    @Transactional
    public History save(History history) {
        return historyRepository.save(history);
    }

    @Transactional
    public void deleteById(Long id) {
        historyRepository.deleteById(id);
    }
}
