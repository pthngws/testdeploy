package com.group4.service;

import com.group4.entity.OrderEntity;
import com.group4.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderEntity> searchOrders(String keyword, String status) {
        // Nếu cả keyword và status đều không được cung cấp, trả về tất cả đơn hàng
        if ((keyword == null || keyword.isEmpty()) && (status == null || status.isEmpty())) {
            return orderRepository.findAll();
        }

        //tìm bằng keyword và status
        if ((keyword != null && !keyword.isEmpty()) && (status != null && !status.isEmpty())) {
            return orderRepository.findByOrderIdAndStatus(keyword, status);

        }

        // Nếu chỉ có status, tìm theo trạng thái
        if (status != null && !status.isEmpty()) {
            return orderRepository.findByStatus(status);
        }

        // Nếu chỉ có keyword, tìm theo mã đơn hàng
        if (keyword != null && !keyword.isEmpty()) {
            return orderRepository.findByOrderId(keyword);
        }
        return null;

    }
    public List<OrderEntity> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Optional<OrderEntity> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

}
