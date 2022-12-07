package com.tot.system.service;


import com.tot.system.model.Security;
import com.tot.system.repository.SecurityRepository;
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
class SecurityServiceTest {
    @MockBean
    private SecurityRepository securityRepository;

    @Autowired
    private SecurityService securityService;

    @Test
    void shouldFindAllSecurities() {
        List<Security> expected = new ArrayList<>();
        when(securityRepository.findAll()).thenReturn(expected);
        assertEquals(expected, securityService.findAll());
    }

    @Test
    void shouldFindSecurityById() {
        Optional<Security> expected = Optional.of(new Security());
        when(securityRepository.findBySecId("21")).thenReturn(expected);
        assertEquals(expected, securityService.findBySecId("21"));
    }

    @Test
    void shouldSaveSecurity() {
        Security expected = new Security();
        when(securityRepository.save(expected)).thenReturn(expected);
        assertEquals(expected, securityService.save(expected));
    }

    @Test
    void shouldDeleteSecurityById() {
        doNothing().when(securityRepository).deleteBySecId("21");
        securityService.deleteBySecId("21");
        verify(securityRepository, times(1)).deleteBySecId("21");
    }
}