package com.tot.system.configuration.item_writer;

import com.tot.system.model.Security;
import com.tot.system.repository.SecurityRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityItemWriter implements ItemWriter<Security> {
    private final SecurityRepository securityRepository;

    @Autowired
    public SecurityItemWriter(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public void write(List<? extends Security> securities) {
        for (Security security : securities) {
            securityRepository.save(security);
        }
    }
}
