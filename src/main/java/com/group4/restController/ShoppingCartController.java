package com.group4.restController;

import com.group4.entity.ProductEntity;
import com.group4.entity.ShoppingCartEntity;
import com.group4.entity.UserEntity;
import com.group4.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService shoppingCartService;

    @PostMapping("/addProduct")
    public ShoppingCartEntity addProductToCart(@RequestParam Long userId, @RequestParam Long productID) {
        UserEntity user = new UserEntity();
        user.setUserID(userId);
        ProductEntity product = new ProductEntity();
        product.setProductID(productID);
        System.out.println(product.getProductID());


        return shoppingCartService.addProductToCart(user, product);
    }
}
