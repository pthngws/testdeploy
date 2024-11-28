package com.group4.service;

import com.group4.model.OrderModel;

import java.util.List;

public interface IOrderHistoryService {
    List<OrderModel> getPurchasedProducts(Long userId);
}