package com.group4.service.impl;

import com.group4.entity.OrderEntity;
import com.group4.repository.HistoryRepository;
import com.group4.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements IHistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<OrderEntity> getPurchaseHistory(Long userID) {
        return historyRepository.getHistory(userID);
    }
}
