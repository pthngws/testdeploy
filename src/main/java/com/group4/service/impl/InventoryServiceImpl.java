package com.group4.service.impl;

import com.group4.repository.InventoryRepository;
import com.group4.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Object[]> findProductNameAndQuantityByProductId(Long productId) {
        return inventoryRepository.findProductNameAndQuantityByProductId(productId);
    }
}
