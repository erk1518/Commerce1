package com.verified.ach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class achController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "World", required = false) String name, Model model) {
        Model user = model.addAttribute("user", name);
        return "hello";
    }
}