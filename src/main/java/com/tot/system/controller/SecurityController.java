package com.tot.system.controller;

import com.tot.system.model.Security;
import com.tot.system.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/security")
public class SecurityController {
    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public String getSecuritiesTable(Model model) {
        model.addAttribute("securities", securityService.findAll());
        return "security/securities";
    }

    @GetMapping("/newSecurity")
    public String getCreateSecurityForm(@ModelAttribute("security") Security security) {
        return "security/newSecurity";
    }

    @PostMapping
    public String createSecurity(@ModelAttribute("security") Security security) {
        securityService.save(security);
        return "redirect:/security";
    }

    @GetMapping("/editSecurity/{secId}")
    public String getEditSecurityForm(@PathVariable("secId") String secId, Model model) {
        model.addAttribute("security", securityService.findBySecId(secId).get());
        return "security/editSecurity";
    }

    @PostMapping("/editSecurity")
    public String editSecurity(@ModelAttribute("security") Security security) {
        securityService.save(security);
        return "redirect:/security";
    }

    @GetMapping("/deleteSecurity/{secId}")
    public String deleteSecurity(@PathVariable("secId") String secId) {
        securityService.deleteBySecId(secId);
        return "redirect:/security";
    }
}
