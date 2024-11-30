package com.group4.service;

import com.group4.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    public List<OrderEntity> getAllOrders();
    public List<OrderEntity> searchOrders(String keyword, String status);
    public List<OrderEntity> getOrdersByCustomerId(Long customerId);
    public Optional<OrderEntity> getOrderById(Long orderId);
    public List<OrderEntity> getOrdersByUserId(Long userID);
    public OrderEntity getOrderDetails(Long orderID);
    public OrderEntity placeOrder(OrderEntity order);
    public void confirmCancelOrder(Long orderId);
    public void rejectCancelOrder(Long orderId);
    public OrderEntity createOrder(Long userId, List<Long> productIds);
    public void cancelOrder(Long orderId, String accountNumber, String accountName,String bankName);
    public int getTotalOrderValue(OrderEntity order);
}
