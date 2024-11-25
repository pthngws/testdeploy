package com.group4.controller;

import com.group4.entity.ProductEntity;
import com.group4.service.IShoppingCartService;
import com.group4.service.impl.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    IShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();

    @GetMapping("/customer/{customerId}")
    public String getProductsByCustomerId(@PathVariable Long customerId, Model model) {
        List<ProductEntity> products = shoppingCartService.findProductsByCustomerId(customerId);
        //model.addAttribute("products", products);
        return "cart";
    }
}
