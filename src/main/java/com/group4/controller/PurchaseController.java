package com.group4.controller;


import com.group4.entity.*;
import com.group4.repository.ProductRepository;
import com.group4.service.IOrderService;
import com.group4.service.IProductService;
import com.group4.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("purchase")
public class PurchaseController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/checkout")
    public String showOrderDetails(
            @RequestParam Long userId,
            @RequestParam(required = false) Long productId, // Trường hợp mua 1 sản phẩm
            @RequestParam(required = false) List<Long> productIds, // Trường hợp mua nhiều sản phẩm
            Model model) {

        UserEntity user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("error", "User not found!");
            return "error";
        }

        model.addAttribute("user", user);

        // Lấy địa chỉ giao hàng của người dùng
        AddressEntity address = user.getAddress();
        model.addAttribute("address", address);

        // Danh sách các ID sản phẩm được chọn
        List<Long> selectedProductIds = new ArrayList<>();
        if (productId != null) {
            selectedProductIds.add(productId);
        }
        if (productIds != null) {
            selectedProductIds.addAll(productIds);
        }

        if (selectedProductIds.isEmpty()) {
            model.addAttribute("error", "No products selected!");
            return "error";
        }

        // Tạo đơn hàng tạm thời
        OrderEntity tempOrder = new OrderEntity();
        double total = 0;
        List<LineItemEntity> lineItems = new ArrayList<>();

        for (Long selectedProductId : selectedProductIds) {
            ProductEntity product = productRepository.findById(selectedProductId).orElse(null);
            if (product == null) {
                model.addAttribute("error", "Product not found: " + selectedProductId);
                return "error";
            }

            // Tạo line item cho sản phẩm
            LineItemEntity lineItem = new LineItemEntity();
            lineItem.setProduct(product);
            lineItem.setQuantity(1); // Mặc định là 1 sản phẩm
            lineItems.add(lineItem);
//            total += lineItem.getTotal();
        }

        // Gán danh sách line items và tổng giá trị cho đơn hàng
        tempOrder.setListLineItems(lineItems);

        // Thêm thông tin đơn hàng vào model để hiển thị
        model.addAttribute("order", tempOrder);
        model.addAttribute("userId", userId);
        model.addAttribute("total", total);
        return "checkout";
    }

    // Xử lý thanh toán
    @PostMapping("/pay")
    public String payOrder(@RequestParam Long userId,
                           @RequestParam List<Long> productIds,
                           RedirectAttributes redirectAttributes) {
        UserEntity user = userService.findById(userId);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found!");
            return "redirect:/error";
        }
        List<ProductEntity> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No products found for the given IDs.");
            return "redirect:/error";
        }

        double total = products.stream().mapToDouble(ProductEntity::getPrice).sum();
        boolean paymentSuccess = PaymentGateway(total);

        if (paymentSuccess) {
            OrderEntity order = orderService.createOrder(userId, productIds);
            if (order == null) {
                redirectAttributes.addFlashAttribute("error", "Failed to create order.");
                return "redirect:/error";
            }

            // Chuyển đến trang thanh toán
            redirectAttributes.addFlashAttribute("success", "Payment successful!");
            return "redirect:/payment?orderId=" + order.getOrderId() + "&total=" + total;
        } else {
            // Nếu thanh toán thất bại
            redirectAttributes.addFlashAttribute("error", "Payment failed. Please try again.");
            return "redirect:/checkout?userId=" + userId;
        }
    }

    @PostMapping("/payment/confirm")
    public String confirmPayment(
            @RequestParam Long userId,
            @RequestParam List<Long> productIds,
            @RequestParam double total,
            RedirectAttributes redirectAttributes) {

        // Gửi yêu cầu đến API thanh toán
        boolean paymentSuccess = PaymentGateway(total);

        if (paymentSuccess) {
            // Nếu thanh toán thành công, tạo đơn hàng
            OrderEntity order = orderService.createOrder(userId, productIds);
            if (order == null) {
                redirectAttributes.addFlashAttribute("error", "Failed to create order.");
                return "redirect:/error";
            }

            // Chuyển đến trang xác nhận đơn hàng
            redirectAttributes.addFlashAttribute("success", "Payment successful!");
            return "redirect:/order-confirmation?orderId=" + order.getOrderId();
        } else {
            // Nếu thanh toán thất bại
            redirectAttributes.addFlashAttribute("error", "Payment failed. Please try again.");
            return "redirect:/checkout?userId=" + userId;
        }
    }

    @GetMapping("/details")
    public String viewOrderDetails(@RequestParam Long orderId, Model model) {
        // Lấy thông tin đơn hàng theo orderId
        OrderEntity order = orderService.getOrderDetails(orderId);
        if (order == null) {
            model.addAttribute("error", "Đơn hàng không tồn tại!");
            return "error";
        }

        model.addAttribute("order", order);
        model.addAttribute("user", order.getCustomer());
        model.addAttribute("address", order.getCustomer().getAddress());
        return "order-detail-view";
    }
    public boolean PaymentGateway(double total) {
        // Giả lập xử lý thanh toán
        try {
            // Gửi yêu cầu đến cổng thanh toán (giả lập API)
            Thread.sleep(1000); // Giả lập thời gian xử lý
            // API trả về thành công
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về thất bại nếu có lỗi
        }
    }
}
