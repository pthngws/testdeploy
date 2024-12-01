package com.group4.controller.admin;

import com.group4.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class InventoryController {

    @Autowired
    private IInventoryService inventoryService;

    @GetMapping("/inventory")
    public String inventory() {
        return "inventory";
    }

}
