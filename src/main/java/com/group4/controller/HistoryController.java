package com.group4.controller;


import com.group4.entity.OrderEntity;
import com.group4.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewOrderHistory(@RequestParam Long userID, Model model) {

        List<OrderEntity> orders = orderService.getOrdersByUserId(userID);
        if (orders.isEmpty()) {
            model.addAttribute("message", "Bạn chưa mua đơn hàng nào.");
        } else {
            model.addAttribute("orders", orders);
        }
        return "order-history";
    }
}
