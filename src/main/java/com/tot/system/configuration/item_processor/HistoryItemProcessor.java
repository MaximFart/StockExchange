package com.tot.system.configuration.item_processor;


import com.tot.system.model.History;
import com.tot.system.model.Security;
import com.tot.system.repository.HistoryRepository;
import com.tot.system.repository.SecurityRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HistoryItemProcessor implements ItemProcessor<History, History> {
    private final SecurityRepository securityRepository;

    @Autowired
    public HistoryItemProcessor(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public History process(History history) {
        Optional<Security> security = securityRepository.findBySecId(history.getSecurity().getSecId());
        security.ifPresent(history::setSecurity);
        return history;
    }
}
