package org.medical.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String mainPage(){
        return "main-page";
    }

    @GetMapping("/registration")
    public String regPage(){
        return "registration";
    }

    @GetMapping("/research_list")
    public String researches(){
        return "researches";
    }
}
