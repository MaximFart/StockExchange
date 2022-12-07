package com.tot.system.controller;

import com.tot.system.service.ResumeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resume")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/{secId}")
    public String getAllResumesBySecId(@PathVariable("secId") String secId, Model model) {
        model.addAttribute("resumes", resumeService.getAllResumesBySecId(secId));
        return "resume/resumes";
    }
}
