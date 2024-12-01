package com.group4.repository;

import com.group4.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    @Query("SELECT p.name, i.quantity " +
            "FROM InventoryEntity i " +
            "JOIN i.product p " +
            "WHERE p.productID = :productId")
    List<Object[]> findProductNameAndQuantityByProductId(Long productId);
}
