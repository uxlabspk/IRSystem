package io.github.uxlabspk.irsystem.controller;

import io.github.uxlabspk.irsystem.service.IRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final IRService service;

    @Autowired
    public ApiController(IRService service) {
        this.service = service;
    }

    @PostMapping("/search1")
    public String search1(@RequestParam("query") String query) {
        Set<Integer> results = service.search(query);
        return results.toString();
    }
}