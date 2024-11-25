package com.group4.controller;

import com.group4.entity.UserEntity;
import com.group4.service.impl.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        boolean isValidCredentials = userService.validateCredentials(username, password);
        UserEntity user = userService.findByEmail(username).get();
        if (isValidCredentials) {
            session.setAttribute("username", username);
            session.setAttribute("user", user);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "login";
        }
    }

}

