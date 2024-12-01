package com.group4.repository;

import com.group4.entity.LineItemEntity;
import com.group4.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedProductRepository extends JpaRepository<LineItemEntity, Long> {
    @Query("SELECT l FROM LineItemEntity l WHERE l.order.orderId = :orderId")
    List<LineItemEntity> findPurchasedProductsByOrderId(Long orderId);


}
