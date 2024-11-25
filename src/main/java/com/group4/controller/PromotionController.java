package com.group4.controller;

import com.group4.entity.PromotionEntity;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.group4.model.PromotionModel;
import com.group4.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/promotions")
public class PromotionController {


    @Autowired
    private PromotionService promotionService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Định dạng ngày tháng là "yyyy-MM-dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);  // Đảm bảo ngày tháng phải chính xác
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    // Lấy danh sách tất cả các khuyến mãi
    @GetMapping()
    public String showPromotions(Model model) {
        List<PromotionModel> promotions = promotionService.fetchPromotionList();
        model.addAttribute("promotions", promotions);
        return "promotion";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("promotion", new PromotionModel());
        return "add-promotion";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        PromotionModel promotion = promotionService.findPromotionById(id);
        model.addAttribute("promotion", promotion);
        return "add-promotion";
    }

    // Thêm khuyến mãi mới
    @PostMapping("/adds")
    public String addPromotion(@ModelAttribute("promotion") PromotionModel promotionModel, BindingResult bindingResult, Model model) {
        // Kiểm tra lỗi validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Validation failed. Please check the inputs.");
            return "add-promotion"; // Trả về form nếu có lỗi
        }

        // Lưu khuyến mãi vào cơ sở dữ liệu
        boolean isSaved = promotionService.savePromotion(promotionModel);
        if (isSaved) {
            model.addAttribute("successMessage", "Promotion added successfully!");
            return "redirect:/promotions"; // Chuyển hướng nếu thành công
        } else {
            model.addAttribute("errorMessage", "Failed to add promotion.");
            return "add-promotion";
        }
    }

    @PostMapping("/save")
    public String savePromotion(@ModelAttribute PromotionModel promotion) {
        promotionService.saveOrUpdatePromotion(promotion);
        return "redirect:/promotions";
    }

    @PutMapping("/update")
    public String updatePromotion(@ModelAttribute PromotionModel promotionModel, Model model) {
        boolean isUpdated = promotionService.updatePromotion(promotionModel);
        if (isUpdated) {
            model.addAttribute("successMessage", "Promotion updated successfully!");
            return "redirect:/promotions"; // Chuyển hướng sau khi cập nhật
        } else {
            model.addAttribute("errorMessage", "Failed to update promotion.");
            return "promotion";
        }
    }

    // Xóa khuyến mãi
    @DeleteMapping("/promotions/delete/{id}")
    public String deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return "redirect:/promotions"; // Chuyển hướng sau khi xóa thành công
    }
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
        double updatedAmount = Math.max(0, totalAmount - discountAmount - shippingAmount);

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