package com.tot.system.controller;

import com.tot.system.model.History;
import com.tot.system.service.HistoryService;
import com.tot.system.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;
    private final SecurityService securityService;

    @Autowired
    public HistoryController(HistoryService historyService, SecurityService securityService) {
        this.historyService = historyService;
        this.securityService = securityService;
    }

    @GetMapping
    public String findAllHistories(Model model) {
        model.addAttribute("histories", historyService.findAll());
        return "history/histories";
    }

    @GetMapping("/newHistory")
    public String newHistory(@ModelAttribute("history") History history, Model model) {
        model.addAttribute("securities", securityService.findAll());
        return "history/newHistory";
    }

    @PostMapping
    public String createNewHistory(@ModelAttribute("history") History history){
        historyService.save(history);
        return "redirect:/history";
    }

    @GetMapping("/editHistory/{id}")
    public String getEditHistoryForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("history", historyService.findById(id).get());
        model.addAttribute("securities", securityService.findAll());
        return "history/editHistory";
    }

    @PutMapping("/editHistory")
    public String editHistory(@ModelAttribute("history") History history) {
        historyService.save(history);
        return "redirect:/history";
    }

    @DeleteMapping("/deleteHistory/{id}")
    public String deleteHistory(@PathVariable("id") long id) {
        historyService.deleteById(id);
        return "redirect:/history";
    }
}
