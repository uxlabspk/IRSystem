package io.github.uxlabspk.irsystem.controller;

import io.github.uxlabspk.irsystem.service.IRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class IRController {
    private final IRService service;

    @Autowired
    public IRController(IRService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "search";
    }

    @PostMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        Set<Integer> results = service.search(query);
        model.addAttribute("results", results);
        return "search";
    }
}
