package com.tot.system.controller;

import com.tot.system.model.History;
import com.tot.system.model.Security;
import com.tot.system.service.HistoryService;
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
class HistoryControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private HistoryService historyService;

    @Autowired
    private HistoryController historyController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build();
    }

    @Test
    void shouldGetPageHistories() throws Exception {
        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("history/histories"));
    }

    @Test
    void shouldGetPageWithCreateFormHistory() throws Exception {
        mockMvc.perform(get("/history/newHistory"))
                .andExpect(status().isOk())
                .andExpect(view().name("history/newHistory"));
    }

    @Test
    void shouldCreateHistoryAndRedirectPageHistories() throws Exception {
        when(securityService.findBySecId("a")).thenReturn(Optional.of(new Security()));
        mockMvc.perform(post("/history"))
                .andExpect(model().attributeExists("history"))
                .andExpect(redirectedUrl("/history"));
    }

    @Test
    void shouldGetPageWithEditFormHistory() throws Exception {
        when(historyService.findById(21l)).thenReturn(Optional.of(new History()));
        mockMvc.perform(get("/history/editHistory/21"))
                .andExpect(status().isOk())
                .andExpect(view().name("history/editHistory"));
    }

    @Test
    void shouldEditHistoryAndRedirectPageHistories() throws Exception {
        when(securityService.findBySecId("a")).thenReturn(Optional.of(new Security()));
        mockMvc.perform(post("/history/editHistory"))
                .andExpect(model().attributeExists("history"))
                .andExpect(redirectedUrl("/history"));
    }

    @Test
    void shouldDeleteHistoryAndRedirectPageHistories() throws Exception {
        mockMvc.perform(get("/history/deleteHistory/1"))
                .andExpect(redirectedUrl("/history"));
    }
}