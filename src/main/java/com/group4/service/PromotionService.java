package com.group4.service;

import com.group4.entity.PromotionEntity;
import com.group4.model.PromotionModel;
import com.group4.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    // Lấy danh sách khuyến mãi từ DB
    public List<PromotionModel> fetchPromotionList() {
        List<PromotionEntity> promotionEntities = promotionRepository.retrievePromotionsFromDB();
        return promotionEntities.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
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
    public boolean deletePromotion(Long id) {
        return promotionRepository.deletePromotion(id);
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
}





//package com.group4.service;
//
//import com.group4.entity.PromotionEntity;
//import com.group4.model.PromotionModel;
//import com.group4.repository.PromotionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PromotionService {
//
//    @Autowired
//    private PromotionRepository promotionRepository;
//
//    public Page<PromotionEntity> fetchPromotionList(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return promotionRepository.findAll(pageable);
//    }
//
//    // Lưu hoặc cập nhật một khuyến mãi
//    @Transactional
//    public boolean saveOrUpdatePromotion(PromotionModel promotionModel) {
//        // Kiểm tra xem khuyến mãi đã tồn tại hay chưa, nếu có thì cập nhật
//        Optional<PromotionEntity> existingPromotion = promotionRepository.findByPromotionID(promotionModel.getPromotionID());
//
//        PromotionEntity promotionEntity = new PromotionEntity();
//
//        if (existingPromotion.isPresent()) {
//            promotionEntity = existingPromotion.get(); // Nếu đã tồn tại, lấy entity hiện tại để cập nhật
//        }
//
//        // Cập nhật các trường từ PromotionModel
//        promotionEntity.setDiscountAmount(promotionModel.getDiscountAmount());
//        promotionEntity.setValidFrom(promotionModel.getValidFrom());
//        promotionEntity.setValidTo(promotionModel.getValidTo());
//        promotionEntity.setPromotionCode(promotionModel.getPromotionCode());
//        promotionEntity.setDescription(promotionModel.getDescription());
//
//        // Lưu (hoặc cập nhật) khuyến mãi
//        promotionRepository.save(promotionEntity);
//        return true;
//    }
//
//    // Tìm khuyến mãi theo ID
//    public PromotionModel findPromotionById(Long id) {
//        Optional<PromotionEntity> promotionEntity = promotionRepository.findByPromotionID(id);
//        return promotionEntity.map(entity -> new PromotionModel(
//                entity.getPromotionID(),
//                entity.getDiscountAmount(),
//                entity.getValidFrom(),
//                entity.getValidTo(),
//                entity.getPromotionCode(),
//                entity.getDescription())
//        ).orElse(null);
//    }
//
//    // Cập nhật thông tin khuyến mãi
//    @Transactional
//    public boolean updatePromotion(PromotionModel promotionModel) {
//        Optional<PromotionEntity> existingPromotion = promotionRepository.findByPromotionID(promotionModel.getPromotionID());
//        if (existingPromotion.isPresent()) {
//            PromotionEntity promotionEntity = existingPromotion.get();
//            promotionEntity.setDiscountAmount(promotionModel.getDiscountAmount());
//            promotionEntity.setValidFrom(promotionModel.getValidFrom());
//            promotionEntity.setValidTo(promotionModel.getValidTo());
//            promotionEntity.setPromotionCode(promotionModel.getPromotionCode());
//            promotionEntity.setDescription(promotionModel.getDescription());
//
//            promotionRepository.save(promotionEntity);
//            return true;
//        }
//        return false;
//    }
//
//    // Kiểm tra sự tồn tại của khuyến mãi theo promotionID
//    public boolean promotionExists(Long promotionID) {
//        return promotionRepository.existsByPromotionID(promotionID);
//    }
//}
