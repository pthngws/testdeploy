package com.group4.restController;

import com.group4.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProductRestController {
    @Autowired
    private IProductService productService;

    // Thống kê số lượng sản phẩm theo tên sản phẩm
    @GetMapping("/admin/api/product/stats-by-name")
    public ResponseEntity<List<Map<String, Object>>> statsByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Map<String, Object>> stats = productService.countProductsByName(page, size);
        return ResponseEntity.ok(stats);
    }

    // Thống kê số lượng sản phẩm theo tên thể loại với phân trang
    @GetMapping("/admin/api/product/stats-by-category")
    public ResponseEntity<List<Map<String, Object>>> statsByCategoryName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Map<String, Object>> stats = productService.countProductsByCategoryName(page, size);
        return ResponseEntity.ok(stats);
    }

    // Thống kê số lượng sản phẩm theo tên nhà sản xuất với phân trang
    @GetMapping("/admin/api/product/stats-by-manufacturer")
    public ResponseEntity<List<Map<String, Object>>> statsByManufacturerName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Map<String, Object>> stats = productService.countProductsByManufacturerName(page, size);
        return ResponseEntity.ok(stats);
    }
}
