package com.group4.service;

import com.group4.model.OrderModel;

import java.util.List;

public interface IOrderTrackingService {
    List<OrderModel> getPurchasedProducts(Long userId);
}