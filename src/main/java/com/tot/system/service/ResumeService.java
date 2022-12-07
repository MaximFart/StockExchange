package com.tot.system.service;

import com.tot.system.model.Resume;
import com.tot.system.model.Security;
import com.tot.system.model.exception.NotFoundException;
import com.tot.system.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeService {
    private final SecurityRepository securityRepository;

    @Autowired
    public ResumeService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public List<Resume> getAllResumesBySecId(String secId) {
        Security security = securityRepository.findBySecId(secId).orElseThrow(NotFoundException::new);
        List<Resume> resumes = security.getHistories().stream()
                .map(history -> {
                    Resume resume = new Resume();
                    resume.setSecId(security.getSecId());
                    resume.setRegNumber(security.getRegNumber());
                    resume.setName(security.getName());
                    resume.setEmitentTitle(security.getEmitentTitle());
                    resume.setTradeDate(history.getTradeDate());
                    resume.setNumTrades(history.getNumTrades());
                    resume.setOpen(history.getOpen());
                    resume.setClose(history.getClose());
                    return resume;
                })
                .collect(Collectors.toList());
        if (resumes.isEmpty()) {
            Resume resume = new Resume();
            resume.setSecId(security.getSecId());
            resume.setRegNumber(security.getRegNumber());
            resume.setName(security.getName());
            resume.setEmitentTitle(security.getEmitentTitle());
            resumes.add(resume);
        }
        return resumes;
    }
}
