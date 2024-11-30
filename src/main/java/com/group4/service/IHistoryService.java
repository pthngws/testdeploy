package com.group4.service;

import com.group4.entity.OrderEntity;

import java.util.List;

public interface IHistoryService {
    List<OrderEntity> getPurchaseHistory(Long userID);
}
