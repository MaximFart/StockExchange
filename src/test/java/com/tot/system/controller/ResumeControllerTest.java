package com.tot.system.controller;

import com.tot.system.service.ResumeService;
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
class ResumeControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private ResumeService resumeService;

    @Autowired
    private ResumeController resumeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(resumeController).build();
    }

    @Test
    void shouldReturnPageResumes() throws Exception {
        when(resumeService.getAllResumesBySecId("1")).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/resume/21"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("resumes"))
                .andExpect(view().name("resume/resumes"));
    }
}