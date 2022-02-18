package com.github.merkurevsergei.controllers;

import com.github.merkurevsergei.service.AccidentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private final AccidentService accidentService;

    public IndexController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", principal);
        model.addAttribute("accidents", accidentService.findAllAccidents());
        return "index";
    }
}
