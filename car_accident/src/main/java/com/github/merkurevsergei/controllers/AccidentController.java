package com.github.merkurevsergei.controllers;

import com.github.merkurevsergei.model.Accident;
import com.github.merkurevsergei.model.AccidentRule;
import com.github.merkurevsergei.service.AccidentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AccidentController {

    private final AccidentService accidentService;

    public AccidentController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidentService.findAllTypes());
        model.addAttribute("rules", accidentService.findAllRules());
        return "accident/create";
    }

    @GetMapping("/update")
    public String edit(@RequestParam("id") int id, Model model) {
        final Optional<Accident> oAccident = accidentService.findAccidentById(id);
        if (oAccident.isEmpty()) {
            model.addAttribute("message", "The accident not found");
            return "404";
        }
        model.addAttribute("accident", oAccident.get());
        model.addAttribute("types", accidentService.findAllTypes());
        model.addAttribute("rules", accidentService.findAllRules());
        return "accident/update";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = Optional.ofNullable(req.getParameterValues("ruleIds")).orElse(new String[0]);
        final Set<AccidentRule> rules = Arrays.stream(ids)
                .map(s -> AccidentRule.of(Integer.parseInt(s), ""))
                .collect(Collectors.toSet());
        accident.setRules(rules);
        accidentService.save(accident);
        return "redirect:/";
    }
}
