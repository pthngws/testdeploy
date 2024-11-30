package com.group4.repository;

import com.group4.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.customer.userID = :userId")
    List<OrderEntity> findPurchasedProductsByUserId(Long userId);
}