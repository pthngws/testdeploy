package com.group4.service;

import com.group4.entity.InventoryEntity;
import com.group4.repository.InventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IInventoryService {
    Page<InventoryEntity> getInventory(String search, Pageable pageable);

    Optional<InventoryEntity> findById(Long id);
    InventoryEntity save(InventoryEntity inventoryEntity);
}
