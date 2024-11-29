package com.group4.restController;

import com.group4.entity.OrderEntity;
import com.group4.repository.OrderRepository;
import com.group4.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController

public class OrderRestController {
    @Autowired
    IOrderService orderService;
    // API kiểm tra trạng thái đơn hàng
    @GetMapping("/status/{orderId}")
    public ResponseEntity<Map<String, Object>> checkOrderStatus(@PathVariable Long orderId) {
        OrderEntity order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return ResponseEntity.ok(Map.of("status", order.getShippingStatus()));
    }
}
