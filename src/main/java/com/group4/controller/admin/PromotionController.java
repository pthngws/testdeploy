package com.group4.controller.admin;

import com.group4.entity.PromotionEntity;
import com.group4.service.IPromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.group4.model.PromotionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/promotions")
public class PromotionController {

    @Autowired
    private IPromotionService promotionService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Định dạng ngày tháng là "yyyy-MM-dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);  // Đảm bảo ngày tháng phải chính xác
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    // Lấy danh sách tất cả các khuyến mãi
    @GetMapping()
    public String showPromotions(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PromotionEntity> promotionPage = promotionService.fetchPromotionList(pageable);

        model.addAttribute("promotions", promotionPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", promotionPage.getTotalPages());
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
    @PostMapping("/save")
    public String savePromotion(@Valid @ModelAttribute("promotion") PromotionModel promotionModel,
                                BindingResult bindingResult, Model model) {
        Date currentDate = new Date();

        // Kiểm tra validFrom và validTo
        if (promotionModel.getValidFrom().before(currentDate) && promotionModel.getPromotionID() == null) {
            model.addAttribute("errorMessage", "Ngày bắt đầu áp dụng phải từ hôm nay trở đi.");
            return "add-promotion";
        }

        if (promotionModel.getValidFrom().after(promotionModel.getValidTo()) && promotionModel.getPromotionID() == null) {
            model.addAttribute("errorMessage",  "Ngày kết thúc phải sau ngày bắt đầu.");
            return "add-promotion";
        }

        // Kiểm tra mã khuyến mãi trùng
        if (promotionService.isPromotionCodeExists(promotionModel.getPromotionCode()) && promotionModel.getPromotionID() == null) {
            model.addAttribute("errorMessage", "Mã khuyến mãi đã tồn tại.");
            return "add-promotion";
        }

        boolean isSaved = promotionService.saveOrUpdatePromotion(promotionModel);
        if (isSaved) {
            model.addAttribute("successMessage", "Khuyến mãi được lưu thành công!");
            return "redirect:/admin/promotions?success";
        } else {
            model.addAttribute("errorMessage", "Không thể lưu khuyến mãi.");
            return "redirect:/admin/promotions?error";
        }
    }

    // Xóa khuyến mãi
    @PostMapping("/delete/{id}")
    public String deletePromotion(@PathVariable("id") Long promotionID, Model model) {
        boolean status = promotionService.deletePromotion(promotionID);

        if (status) {
            return "redirect:/admin/promotions?delete-success";
        } else {
            return "redirect:/admin/promotions?delete-error";
        }
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

