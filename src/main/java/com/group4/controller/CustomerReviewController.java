package com.group4.controller;

import com.group4.entity.RateEntity;
import com.group4.entity.UserEntity;
import com.group4.entity.ProductEntity;
import com.group4.service.ICustomerReviewService;
import com.group4.service.IProductService;
import com.group4.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CustomerReviewController {

    @Autowired
    private ICustomerReviewService customerReviewService;

    @Autowired
    private IUserService userService;  // Service lấy thông tin người dùng

    @Autowired
    private IProductService productService;  // Service lấy thông tin sản phẩm

    @PostMapping("/submitReview")
    public String submitReview(@RequestParam Long productId,
                               @RequestParam int rating,
                               @RequestParam String reviewContent,
                               @RequestParam Long userId) {

        // Check if the product exists

        ProductEntity product = new ProductEntity();
        product.setProductID(productId);
        // Lấy thông tin người dùng
        UserEntity user = userService.findById(userId);

        // Tạo đối tượng RateEntity và gán giá trị
        RateEntity review = new RateEntity();
        review.setUser(user);
        review.setProduct(product);
        review.setRate(rating);
        review.setContent(reviewContent);

        // Lưu đánh giá vào cơ sở dữ liệu
        customerReviewService.saveReview(review);

        return "redirect:/purchasedProduct"; // Chuyển hướng về trang chi tiết sản phẩm
    }

}
