package com.group4.controller;

import com.group4.entity.CustomerEntity;
import com.group4.entity.OrderEntity;
import com.group4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Hiển thị danh sách đơn hàng
    @GetMapping
    public String listOrders(Model model) {
        List<OrderEntity> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "TamaOrderList";
    }

    // Tìm kếm đơn hàng
    @GetMapping("/search")
    public String searchOrders(@RequestParam(value = "search", required = false) String keyword,
                               @RequestParam(value = "status", required = false) String status,
                               Model model) {
        List<OrderEntity> orders = orderService.searchOrders(keyword, status);
        model.addAttribute("orders", orders);
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("status", status);

        return "TamaOrderList";
    }

    // Chi tiết đơn hàng
    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model) {

         //Lấy thông tin đơn hàng
        OrderEntity order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        model.addAttribute("order", order);
        return "TamaOrderDetail";
    }
}
