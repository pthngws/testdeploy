package com.group4.controller.admin;

import com.group4.entity.OrderEntity;
import com.group4.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    // Hiển thị danh sách đơn hàng
    @GetMapping
    public String listOrders(Model model) {
        List<OrderEntity> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "TamaOrderList";
    }

    // Tìm kếm đơn hàng
    @GetMapping("/search")
    public String searchOrders(@RequestParam(value = "search", required = false) String keyword,
                               @RequestParam(value = "status", required = false) String status,
                               Model model) {
        List<OrderEntity> orders = orderService.searchOrders(keyword, status);
        model.addAttribute("orders", orders);
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("status", status);

        return "TamaOrderList";
    }

    // Chi tiết đơn hàng
    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model) {

         //Lấy thông tin đơn hàng
        OrderEntity order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        model.addAttribute("order", order);

        int totalvalue = order.getTotalOrderValue();
        model.addAttribute("totalvalue", totalvalue);

        return "TamaOrderDetail";
    }

    @PostMapping("/{id}/confirm")
    public String confirmCancelOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            OrderEntity order = orderService.getOrderById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

            // Chỉ xử lý nếu trạng thái là "Yêu cầu hủy"
            if (!"Yêu cầu hủy".equals(order.getShippingStatus())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Chỉ có thể xác nhận hủy khi đơn hàng ở trạng thái 'Yêu cầu hủy'.");
                return "redirect:/orders";
            }

            orderService.confirmCancelOrder(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng đã được xác nhận hủy.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi xác nhận hủy đơn hàng.");
        }
        return "redirect:/orders";
    }

    @PostMapping("/{id}/cancel")
    public String rejectCancelOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            OrderEntity order = orderService.getOrderById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

            // Chỉ xử lý nếu trạng thái là "Yêu cầu hủy"
            if (!"Yêu cầu hủy".equals(order.getShippingStatus())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Chỉ có thể từ chối hủy khi đơn hàng ở trạng thái 'Yêu cầu hủy'.");
                return "redirect:/orders";
            }

            orderService.rejectCancelOrder(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng đã được từ chối hủy.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi từ chối hủy đơn hàng.");
        }
        return "redirect:/orders";
    }
    @GetMapping("/order-details")
    public String showOrderDetails(
            @RequestParam Long userId,
            @RequestParam(required = false) Long productId, // Trường hợp mua 1 sản phẩm
            @RequestParam(required = false) List<Long> productIds, // Trường hợp mua nhiều sản phẩm
            Model model) {

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

        // Tạo đơn hàng và hiển thị chi tiết
        OrderEntity order = orderService.createOrder(userId, selectedProductIds);
        if (order == null) {
            model.addAttribute("error", "Order not found");
            return "error";
        }

        // Tính tổng giá trị đơn hàng
//        double total = order.getListLineItems().stream()
//                .mapToDouble(LineItemEntity::getTotal)
//                .sum();
        double total = 0;
        // Thêm vào model
        model.addAttribute("order", order);
        model.addAttribute("total", total);
        return "order-details";
    }

    // Xử lý thanh toán (chỉ gọi URL thanh toán)
    @PostMapping("/pay")
    public String payOrder(@RequestParam Long orderId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Redirecting to payment...");
        return "redirect:/payment?orderId=" + orderId;
    }
}
