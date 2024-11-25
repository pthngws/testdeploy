package com.group4.controller;

import com.group4.model.UserModel;
import com.group4.service.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonalInfoController {

    @Autowired
    private PersonalInfoService service;

    // Lấy thông tin cá nhân
    @GetMapping("/personal-info")
    public String getPersonalInfo(Model model) {
        UserModel user = service.fetchPersonalInfo(); // Gọi Service để lấy thông tin người dùng
        model.addAttribute("user", user);
        return "personal-info"; // Hiển thị trang personal-info
    }

    // Cập nhật thông tin cá nhân
    @PostMapping("/personal-info")
    public String updatePersonalInfo(@ModelAttribute("user") UserModel user) {
        boolean status = service.savePersonalInfo(user); // Lưu thông tin mới
        if (status) {
            return "redirect:/personal-info?success"; // Chuyển hướng với trạng thái thành công
        } else {
            return "redirect:/personal-info?error"; // Chuyển hướng với trạng thái thất bại
        }
    }

}
