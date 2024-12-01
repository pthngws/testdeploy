package com.group4.repository;

import com.group4.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, Long> {

    // Kiểm tra xem đánh giá đã tồn tại dựa trên userId và productId
    boolean existsByUser_UserIDAndProduct_ProductID(Long userId, Long productId);
}

