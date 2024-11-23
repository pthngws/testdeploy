package com.group4.controller;

import com.group4.service.IShoppingCartService;
import com.group4.service.impl.ShoppingCartServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public class ShoppingCartController {

    @GetMapping("/product")
    public String getProductDetail() {
        // Giả sử bạn có một service để lấy thông tin chi tiết của sản phẩm
        IShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
        // Trả về tên của view template
        return "cart";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Giả lập dữ liệu
        model.addAttribute("todayRevenue", 18230); // Tổng doanh thu hôm nay (giả lập)
        model.addAttribute("todayGrowth", 12.5); // Tăng trưởng hôm nay so với hôm qua (%)
        model.addAttribute("sales", 18230); // Doanh thu tổng
        model.addAttribute("conversionRate", 12); // Tỷ lệ chuyển đổi
        model.addAttribute("salesRate", 0.8); // Tăng trưởng doanh thu (%)
        model.addAttribute("registrationRate", -1); // Đăng ký tài khoản (%)

        List<Map<String, Object>> products = List.of(
                Map.of("name", "Some Product", "price", 13, "sales", "12,000 Sold"),
                Map.of("name", "Another Product", "price", 29, "sales", "123,234 Sold"),
                Map.of("name", "Amazing Product", "price", 1230, "sales", "198 Sold"),
                Map.of("name", "Perfect Item", "price", 199, "sales", "87 Sold")
        );

        model.addAttribute("products", products);

        return "dashboard";
    }
}
