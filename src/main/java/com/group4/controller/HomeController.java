package com.group4.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("home")
    public String home(Model model) {
        return "home";
    }


    // Xử lý về chúng tôi
    @GetMapping("/about-us")
    public String aboutUsPage() {
        return "about-us";
    }

    // Xử lý liên hệ
    @GetMapping("/contact")
    public String contactPage() {
        return "contact-us";
    }
}
