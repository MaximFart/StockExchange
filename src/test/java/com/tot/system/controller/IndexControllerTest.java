package com.tot.system.controller;

import com.tot.system.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class IndexControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private IndexController indexController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    void shouldGetAllSecurityBySecId() throws Exception {
        when(securityService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("securities"))
                .andExpect(view().name("index"));
    }
}