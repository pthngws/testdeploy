package com.group4.service;

import com.group4.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface IHistoryService {
    List<OrderEntity> getPurchaseHistory(Long userID);
    OrderEntity getOrderById(Long orderId);
    void cancelOrder(Long orderId, String accountNumber, String accountName,String bankName);
}
