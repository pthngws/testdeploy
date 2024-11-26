package com.group4.controller.admin;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ProductController {
    @GetMapping("/inventory")
    public String inventory(Model model){
        return "inventory";
    }
}
