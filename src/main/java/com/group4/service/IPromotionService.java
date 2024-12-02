package com.group4.service;

import com.group4.entity.PromotionEntity;
import com.group4.model.PromotionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPromotionService {
    public Page<PromotionEntity> fetchPromotionList(Pageable pageable);
    public boolean isPromotionCodeExists(String promotionCode);
    public boolean saveOrUpdatePromotion(PromotionModel promotionModel);
    public boolean deletePromotion(Long id);
    public PromotionModel findPromotionById(Long id);
    public Optional<PromotionEntity> findByPromotionCode(String promotionCode);
    public void save(PromotionEntity promotion);
}
