//package com.group4.controller;
//
//import com.group4.model.OrderModel;
//import com.group4.service.IPurchasedProductsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/orderTracking")
//public class OrderTrackingController {
//
//    @Autowired
//    private IOrderTrackingService purchasedProductsService;
//
//    @GetMapping()
//    public String getPurchasedProducts(Model model) {
//        List<OrderModel> purchasedProducts = purchasedProductsService.getPurchasedProducts(1L);
//        model.addAttribute("purchasedProducts", purchasedProducts);
//        return "purchasedProducts"; // trả về tên của trang JSP hoặc HTML để hiển thị
//    }
//}