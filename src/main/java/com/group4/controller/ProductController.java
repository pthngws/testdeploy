package com.group4.controller;

import java.util.Optional;

import com.group4.service.ICategoryService;
import com.group4.service.IManufacturersService;
import com.group4.service.IProductDetailService;
import com.group4.service.IProductService;
import com.group4.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.group4.entity.ProductEntity;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController("Product")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IManufacturersService manufacturersService;
    @Autowired
    private IProductDetailService productDetailService;

    // Thống kê số lượng sản phẩm theo tên sản phẩm
    @GetMapping("/stats-by-name")
    public ResponseEntity<List<Map<String, Object>>> statsByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Map<String, Object>> stats = productService.countProductsByName(page, size);
        return ResponseEntity.ok(stats);
    }

    // Thống kê số lượng sản phẩm theo tên thể loại với phân trang
    @GetMapping("/stats-by-category")
    public ResponseEntity<List<Map<String, Object>>> statsByCategoryName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Map<String, Object>> stats = productService.countProductsByCategoryName(page, size);
        return ResponseEntity.ok(stats);
    }

    // Thống kê số lượng sản phẩm theo tên nhà sản xuất với phân trang
    @GetMapping("/stats-by-manufacturer")
    public ResponseEntity<List<Map<String, Object>>> statsByManufacturerName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Map<String, Object>> stats = productService.countProductsByManufacturerName(page, size);
        return ResponseEntity.ok(stats);
    }
  
    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product-list";  // Trang hiển thị danh sách sản phẩm
    }

    @GetMapping("/edit/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        Optional<ProductEntity> optionalProduct = productService.findById(id);

        // Kiểm tra xem sản phẩm có tồn tại không
        if (optionalProduct.isPresent()) {
            ProductEntity product = optionalProduct.get();
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("manufacturers", manufacturersService.findAll());
            model.addAttribute("productDetails", productDetailService.findAll());
            return "product-edit";  // Trang chỉnh sửa sản phẩm
        } else {
            // Nếu không tìm thấy sản phẩm, có thể chuyển hướng đến trang lỗi hoặc danh sách sản phẩm
            return "redirect:/admin/products";  // Ví dụ: quay lại danh sách sản phẩm
        }
    }


    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") ProductEntity productEntity) {
        productService.save(productEntity);
        return "redirect:/admin/products";  // Chuyển hướng về danh sách sản phẩm
    }


    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") ProductEntity productEntity) {
        productService.save(productEntity);
        return "redirect:/admin/products";  // Chuyển hướng về danh sách sản phẩm
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";  // Chuyển hướng về danh sách sản phẩm
    }
}
