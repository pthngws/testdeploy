package com.group4.controller;

import com.group4.entity.CustomerEntity;
import com.group4.entity.OrderEntity;
import com.group4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

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
}
