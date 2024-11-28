package com.group4.service.impl;

import com.group4.entity.PromotionEntity;
import com.group4.model.PromotionModel;
import com.group4.service.IPromotionService;
import org.springframework.data.domain.Page;
import com.group4.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromotionServiceImpl implements IPromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    // Lấy danh sách khuyến mãi từ DB
    public Page<PromotionEntity> fetchPromotionList(Pageable pageable) {
        return promotionRepository.findAll(pageable);
//        List<PromotionEntity> promotionEntities = promotionRepository.findAll(pageable);
////        return promotionEntities.stream()
////                .map(this::convertToModel)
////                .collect(Collectors.toList());
    }

    // Lưu khuyến mãi mới
    public boolean savePromotion(PromotionModel promotionModel) {
        PromotionEntity promotionEntity = convertToEntity(promotionModel);
        return promotionRepository.savePromotionToDB(promotionEntity);
    }

    public boolean isPromotionCodeExists(String promotionCode) {
        return promotionRepository.checkPromotionCodeExist(promotionCode);
    }

    // Cập nhật khuyến mãi
    public boolean updatePromotion(PromotionModel promotionModel) {
        if (promotionRepository.checkPromotionExist(promotionModel.getPromotionID())) {
            PromotionEntity promotionEntity = convertToEntity(promotionModel);
            return promotionRepository.saveUpdatePromotion(promotionEntity);
        }
        return false; // Không tồn tại khuyến mãi
    }


    // Lưu hoặc cập nhật khuyến mãi
    public boolean saveOrUpdatePromotion(PromotionModel promotionModel) {
        // Kiểm tra xem khuyến mãi đã tồn tại hay chưa
        PromotionEntity promotionEntity = new PromotionEntity();
        // Cập nhật các trường từ PromotionModel vào PromotionEntity
        promotionEntity.setDiscountAmount(promotionModel.getDiscountAmount());
        promotionEntity.setValidFrom(promotionModel.getValidFrom());
        promotionEntity.setValidTo(promotionModel.getValidTo());
        promotionEntity.setPromotionCode(promotionModel.getPromotionCode());
        promotionEntity.setDescription(promotionModel.getDescription());

        if(promotionModel.getPromotionID() == null) {

            promotionRepository.save(promotionEntity);
            return false;
        }
        Optional<PromotionEntity> existingPromotion = promotionRepository.findById(promotionModel.getPromotionID());
        if (existingPromotion.isPresent()) {
            // Lấy khuyến mãi hiện tại để cập nhật
            promotionEntity = existingPromotion.get();
        }



        // Lưu (hoặc cập nhật) khuyến mãi vào cơ sở dữ liệu
        promotionRepository.save(promotionEntity);

        return true; // Trả về true nếu lưu hoặc cập nhật thành công
    }

    // Xóa khuyến mãi
    public boolean deletePromotion(Long promotionID) {
        try {
            promotionRepository.deletePromotion(promotionID); // Xóa khỏi cơ sở dữ liệu
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Chuyển đổi từ PromotionModel sang PromotionEntity
    private PromotionEntity convertToEntity(PromotionModel promotionModel) {
        return new PromotionEntity(
                promotionModel.getPromotionID(),
                promotionModel.getDiscountAmount(),
                promotionModel.getRemainingUses(),
                promotionModel.getValidFrom(),
                promotionModel.getValidTo(),
                promotionModel.getPromotionCode(),
                promotionModel.getDescription()
        );
    }

    // Chuyển đổi từ PromotionEntity sang PromotionModel
    private PromotionModel convertToModel(PromotionEntity promotionEntity) {
        return new PromotionModel(
                promotionEntity.getPromotionID(),
                promotionEntity.getDiscountAmount(),
                promotionEntity.getRemainingUses(),
                promotionEntity.getValidFrom(),
                promotionEntity.getValidTo(),
                promotionEntity.getPromotionCode(),
                promotionEntity.getDescription()
        );
    }
    public PromotionModel findPromotionById(Long id) {
        Optional<PromotionEntity> promotionEntity = promotionRepository.findById(id);
        return promotionEntity.map(entity -> new PromotionModel(
                entity.getPromotionID(),
                entity.getDiscountAmount(),
                entity.getRemainingUses(),
                entity.getValidFrom(),
                entity.getValidTo(),
                entity.getPromotionCode(),
                entity.getDescription())
        ).orElse(null);
    }

    @Override
    public Optional<PromotionEntity> findByPromotionCode(String promotionCode) {
        return promotionRepository.findByPromotionCode(promotionCode);
    }

    @Override
    public void save(PromotionEntity promotion) {
        promotionRepository.save(promotion);
    }
}