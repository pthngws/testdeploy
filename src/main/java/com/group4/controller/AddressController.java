package com.group4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller("/address")
public class AddressController {

//    @Autowired
//    private ResourceLoader resourceLoader;
//
//    // Đọc dữ liệu từ Address.json
//    @GetMapping("/address")
//    public String getUserAddress(Model model) throws IOException {
//        // Đọc dữ liệu từ Address.json
//        Map<String, Object> provincesAndDistricts = loadAddressData();
//
//        // Lưu dữ liệu vào model để truyền cho Thymeleaf
//        model.addAttribute("provincesAndDistricts", provincesAndDistricts);
//
//        // Trả về tên view
//        return "personal-info";
//    }
//
//    private Map<String, Object> loadAddressData() throws IOException {
//        Resource resource = resourceLoader.getResource("classpath:static/assets/data/Address.json");
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(resource.getInputStream(), Map.class);
//    }
}
