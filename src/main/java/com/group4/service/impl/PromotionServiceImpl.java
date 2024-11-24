package com.group4.service.impl;

import com.group4.entity.PromotionEntity;
import com.group4.repository.PromotionRepository;
import com.group4.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public Optional<PromotionEntity> findByPromotionCode(String promotionCode) {
        return promotionRepository.findByPromotionCode(promotionCode);
    }

    @Override
    public void save(PromotionEntity promotion) {
        promotionRepository.save(promotion);
    }


}
