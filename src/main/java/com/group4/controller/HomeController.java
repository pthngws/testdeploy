package com.group4.controller;


import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("home")
public class HomeController {
    @GetMapping
    public String home(Model model) {
        return "home";


    @GetMapping("/inventory")
    public String inventory(){
        return "inventory";
    }

}
