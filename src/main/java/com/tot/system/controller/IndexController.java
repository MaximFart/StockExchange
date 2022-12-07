package com.tot.system.controller;

import com.tot.system.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    private final SecurityService securityService;

    public IndexController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public String findAllSecurities(Model model) {
        model.addAttribute("securities", securityService.findAll());
        return "index";
    }
}
