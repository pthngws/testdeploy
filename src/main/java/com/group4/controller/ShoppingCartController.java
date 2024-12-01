package com.group4.controller;

import com.group4.entity.CustomerEntity;
import com.group4.entity.ProductEntity;
import com.group4.entity.ShoppingCartEntity;
import com.group4.repository.ProductRepository;
import com.group4.service.IProductService;
import com.group4.service.IShoppingCartService;
import com.group4.service.impl.ProductServiceImpl;
import com.group4.service.impl.ShoppingCartServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    IShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    @Autowired
    IProductService productService = new ProductServiceImpl();

    /**
     * Lấy sản phẩm trong giỏ hàng dựa trên session của khách hàng
     */
    @GetMapping
    public String getProductsBySession(HttpSession session, Model model) {
        // Lấy thông tin khách hàng từ session
        CustomerEntity currentUser = (CustomerEntity) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login"; // Chuyển hướng đến trang login nếu chưa đăng nhập
        }

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<ProductEntity> products = shoppingCartService.findProductsByCustomerId(currentUser.getUserID());
        model.addAttribute("products", products);
        return "cart";
    }

//    @GetMapping("/{customerId}")
//    public String getProductsByCustomerId(@PathVariable Long customerId, Model model) {
//        List<ProductEntity> products = shoppingCartService.findProductsByCustomerId(customerId);
//        model.addAttribute("products", products);
//        model.addAttribute("customerId", customerId);
//        System.out.println(products);
//        return "cart";
//    }
//    @GetMapping("/remove/{productId}/{customerId}")
//    public String removeProductFromCart(@PathVariable Long productId, @PathVariable Long customerId) {
//        shoppingCartService.removeProductFromCart(customerId, productId);
//        return "redirect:/cart/" + customerId;
//    }

    @GetMapping("/remove/{productId}")
    public String removeProductFromCart(@PathVariable Long productId, HttpSession session) {
        // Lấy thông tin khách hàng từ session
        CustomerEntity currentUser = (CustomerEntity) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login"; // Chuyển hướng đến trang login nếu chưa đăng nhập
        }

        // Xóa sản phẩm khỏi giỏ hàng
        shoppingCartService.removeProductFromCart(currentUser.getUserID(), productId);
        return "redirect:/cart";
    }

    @PostMapping("/add")
    public String addProductToCart(@RequestParam Long productId,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        // Lấy thông tin người dùng từ session
        CustomerEntity currentCustomer = (CustomerEntity) session.getAttribute("user");

        if (currentCustomer == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            return "redirect:/login";
        }

        // Tìm sản phẩm theo productId
        ProductEntity product = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));

        // Thêm sản phẩm vào giỏ hàng
        ShoppingCartEntity shoppingCart = shoppingCartService.addProductToCart(currentCustomer, product);

        // Thông báo thành công
        redirectAttributes.addFlashAttribute("message", "Đã thêm sản phẩm vào giỏ hàng!");

        // Chuyển hướng về trang sản phẩm hoặc giỏ hàng
        return "redirect:/cart"; // Hoặc "redirect:/products" nếu muốn quay lại danh sách sản phẩm
    }
}
