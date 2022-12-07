package com.tot.system.service;

import com.tot.system.model.Security;
import com.tot.system.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityService {
    private final SecurityRepository securityRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Transactional
    public Security save(Security security) {
        return securityRepository.save(security);
    }

    @Transactional
    public void deleteBySecId(String secId) {
        securityRepository.deleteBySecId(secId);
    }

    @Transactional
    public List<Security> findAll() {
        return securityRepository.findAll();
    }

    @Transactional
    public Optional<Security> findBySecId(String secId) {
        return securityRepository.findBySecId(secId);
    }
}

