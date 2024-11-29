package com.group4.restController;

import com.group4.entity.PromotionEntity;
import com.group4.service.IPromotionService;
import com.group4.service.impl.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController

public class PromotionRestController {

    @Autowired
    IPromotionService promotionService = new PromotionServiceImpl();

    @PostMapping("/api/promotions/apply")
    public ResponseEntity<?> applyPromotion(@RequestBody Map<String, Object> request) {
        String promotionCode = (String) request.get("promotionCode");
        double totalAmount = Double.parseDouble(request.get("totalAmount").toString());
        double shippingAmount = Double.parseDouble(request.get("shippingAmount").toString());

        // Lấy thông tin mã giảm giá từ database
        Optional<PromotionEntity> promotionOpt = promotionService.findByPromotionCode(promotionCode);
        if (!promotionOpt.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Promotion code is invalid!"));
        }

        PromotionEntity promotion = promotionOpt.get();
        if (promotion.getValidTo().before(new Date())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Promotion code has expired!"));
        }

        // Tính toán giá mới
        double discountAmount = promotion.getDiscountAmount();
        double updatedAmount = Math.max(0, totalAmount - discountAmount + shippingAmount);

        return ResponseEntity.ok(Map.of("updatedAmount", updatedAmount));
    }

    @PostMapping("/api/orders/checkout")
    public ResponseEntity<?> checkout(@RequestBody Map<String, Object> request) {
        String promotionCode = (String) request.get("promotionCode");
        double totalAmount = Double.parseDouble(request.get("totalAmount").toString());

        // Xử lý thanh toán
        if (promotionCode != null && !promotionCode.isEmpty()) {
            Optional<PromotionEntity> promotionOpt = promotionService.findByPromotionCode(promotionCode);
            if (promotionOpt.isPresent()) {
                PromotionEntity promotion = promotionOpt.get();
                promotion.setRemainingUses(promotion.getRemainingUses() - 1); // Giảm số lượt sử dụng
                promotionService.save(promotion);
            }
        }

        // Trả về phản hồi
        return ResponseEntity.ok(Map.of("message", "Checkout successful!"));
    }
}
