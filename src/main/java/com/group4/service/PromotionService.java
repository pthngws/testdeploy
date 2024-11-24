package com.group4.service;


import com.group4.entity.ProductEntity;
import com.group4.entity.PromotionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PromotionService {
    Optional<PromotionEntity> findByPromotionCode(String promotionCode);

    void save(PromotionEntity promotion);
}



