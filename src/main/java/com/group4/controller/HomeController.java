package com.group4.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String index(){
        return "index";
    }

    @GetMapping("/inventory")
    public String inventory(){
        return "inventory";
    }

}
