package com.group4.controller.user;

import com.group4.entity.OrderEntity;
import com.group4.service.IOrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class UserOrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable Long orderId,
                              @RequestParam String accountNumber,
                              @RequestParam String accountName,
                              @RequestParam String bankName) {
        orderService.cancelOrder(orderId,accountNumber,accountName,bankName);
        return "redirect:/orderhistory";
    }
}
