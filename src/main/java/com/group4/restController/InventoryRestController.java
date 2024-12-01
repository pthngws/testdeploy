package com.group4.restController;

import com.group4.entity.InventoryEntity;
import com.group4.model.InventoryModel;
import com.group4.repository.InventoryRepository;
import com.group4.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/api/inventory")
public class InventoryRestController {

    @Autowired
    private IInventoryService inventoryService;

    // API để lấy danh sách inventory
    @GetMapping
    public Page<InventoryEntity> getInventory(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return inventoryService.getInventory(search, pageable);
    }

    // API để cập nhật số lượng của sản phẩm
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateInventory(@PathVariable Long productId, @RequestBody InventoryModel request) {
        Optional<InventoryEntity> inventoryOptional = inventoryService.findById(productId);

        if (inventoryOptional.isPresent()) {
            InventoryEntity inventory = inventoryOptional.get();
            inventory.setQuantity(request.getQuantity());
            inventoryService.save(inventory);
            return ResponseEntity.ok().build(); // Cập nhật thành công
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Sản phẩm không tìm thấy
        }
    }
}
