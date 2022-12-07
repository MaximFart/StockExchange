package com.tot.system.configuration.item_writer;


import com.tot.system.model.History;
import com.tot.system.repository.HistoryRepository;
import com.tot.system.repository.SecurityRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoryItemWriter implements ItemWriter<History> {
    private final HistoryRepository historyRepository;

    private final SecurityRepository securityRepository;

    @Autowired
    public HistoryItemWriter(HistoryRepository historyRepository, SecurityRepository securityRepository) {
        this.historyRepository = historyRepository;
        this.securityRepository = securityRepository;
    }

    @Override
    public void write(List<? extends History> histories) throws Exception{
        for (History history : histories) {
            if (!historyRepository.findById(history.getId()).isPresent())
                if (securityRepository.findBySecId(history.getSecurity().getSecId()).isPresent())
                    historyRepository.save(history);
        }
    }
}
