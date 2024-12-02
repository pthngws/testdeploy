package com.group4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.group4.entity.InventoryEntity;
import com.group4.entity.ProductEntity;
import com.group4.service.IInventoryService;
import com.group4.service.IProductDetailService;
import com.group4.service.IProductService;

@Controller
public class ProductDetailController {

	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IProductDetailService productDetailService;
	
	@Autowired
	private IInventoryService inventoryService; 
	
	
	@GetMapping("/product/{id}")
    public String getProductDetail(Model model, @PathVariable Long id) {
		Optional<ProductEntity> productOptional = productService.findById(id);
		
		
        if (productOptional.isPresent()) {
            // Nếu có sản phẩm, thêm vào model
        	int reviewCount = productService.getReviewCount(productOptional.get());
        	
        	
            model.addAttribute("product", productOptional.get());
           
            
            //System.out.println(productOptional.get().getStatus());
            model.addAttribute("reviewCount", reviewCount);
            
            
         // Nếu product có status = 1, lấy quantity từ InventoryEntity
            if (productOptional.get().getStatus() == 1) {
                Optional<InventoryEntity> inventoryOptional = inventoryService.findById(productOptional.get().getProductID());
                inventoryOptional.ifPresent(inventory -> model.addAttribute("quantity", inventory.getQuantity()));
            } else {
                model.addAttribute("quantity", null); // Không có số lượng
            }
            return "productDetails";  // Tên file HTML sẽ là product-details.html
        } else {
            // Nếu không tìm thấy sản phẩm, có thể chuyển đến trang lỗi hoặc trang không tìm thấy
             return "redirect:/products";  // Trang không tìm thấy sản phẩm
        }
       
    }
	
}
