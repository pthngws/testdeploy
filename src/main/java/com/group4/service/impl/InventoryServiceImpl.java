package com.group4.service.impl;

import com.group4.entity.InventoryEntity;
import com.group4.repository.InventoryRepository;
import com.group4.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Page<InventoryEntity> getInventory(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return inventoryRepository.findAll(pageable); // No search filter applied
        } else {
            return inventoryRepository.searchInventory(search, pageable); // Apply search filter
        }
    }

    @Override
    public Optional<InventoryEntity> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public InventoryEntity save(InventoryEntity inventoryEntity) {
        return inventoryRepository.save(inventoryEntity);
    }
}
