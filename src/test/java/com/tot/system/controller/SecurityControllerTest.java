package com.tot.system.controller;

import com.tot.system.model.Security;
import com.tot.system.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class SecurityControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private SecurityController securityController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(securityController).build();
    }

    @Test
    void shouldGetPageSecurities() throws Exception {
        mockMvc.perform(get("/security"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/securities"));
    }

    @Test
    void shouldGetPageWithCreateFormSecurities() throws Exception {
        mockMvc.perform(get("/security/newSecurity"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/newSecurity"));
    }

    @Test
    void shouldCreateSecurityAndRedirectPageSecurities() throws Exception {
        mockMvc.perform(post("/security"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("security"))
                .andExpect(redirectedUrl("/security"));
    }

    @Test
    void shouldPageWithEditFormSecurities() throws Exception {
        when(securityService.findBySecId("21")).thenReturn(Optional.of(new Security()));
        mockMvc.perform(get("/security/editSecurity/21"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/editSecurity"));
    }

    @Test
    void shouldEditSecurityAndRedirectPageSecurities() throws Exception {
        mockMvc.perform(post("/security/editSecurity"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("security"))
                .andExpect(redirectedUrl("/security"));
    }

    @Test
    void shouldDeleteSecurityAndRedirectPageSecurities() throws Exception {
        mockMvc.perform(get("/security/deleteSecurity/21"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/security"));
    }
}