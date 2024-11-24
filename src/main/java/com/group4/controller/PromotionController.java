package com.group4.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
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
}

