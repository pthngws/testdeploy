package com.group4.repository;

import com.group4.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {
    @Query("SELECT p FROM PromotionEntity p")
    List<PromotionEntity> retrievePromotionsFromDB();

    // Lưu một khuyến mãi mới vào cơ sở dữ liệu
    default boolean savePromotionToDB(PromotionEntity promotionEntity) {
        try {
            this.save(promotionEntity); // Gọi hàm save() mặc định của JpaRepository
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật khuyến mãi
    default boolean saveUpdatePromotion(PromotionEntity promotionEntity) {
        if (this.existsById(promotionEntity.getPromotionID())) { // Kiểm tra khuyến mãi tồn tại
            this.save(promotionEntity); // Gọi hàm save() để cập nhật
            return true;
        }
        return false; // Không tồn tại thì trả về false
    }

    // Tìm Promotion theo ID
    @Query("SELECT p FROM PromotionEntity p WHERE p.promotionID = :promotionID")
    Optional<PromotionEntity> findPromotionByID(@Param("promotionID") Long promotionID);


    // Kiểm tra khuyến mãi có tồn tại
    default boolean checkPromotionExist(Long id) {
        return this.existsById(id);
    }
    default boolean checkPromotionCodeExist(String promotionCode) {
        return this.existsByPromotionCode(promotionCode);
    }
    boolean existsByPromotionCode(String promotionCode);

    // Xóa khuyến mãi
    default boolean deletePromotion(Long id) {
        this.deleteById(id);
        return false;
    }
    Optional<PromotionEntity> findByPromotionCode(String promotionCode);
}
