package com.group4.service.impl;

import com.group4.entity.ProductEntity;
import com.group4.entity.ShoppingCartEntity;
import com.group4.entity.UserEntity;
import com.group4.repository.ShoppingCartRepository;
import com.group4.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCartEntity addProductToCart(UserEntity user, ProductEntity product) {
        // Find the shopping cart by user
        Optional<ShoppingCartEntity> optionalCart = shoppingCartRepository.findByUser(user);

        ShoppingCartEntity shoppingCart;
        if (optionalCart.isPresent()) {
            // Cart already exists, add the product
            shoppingCart = optionalCart.get();
        } else {
            // Create new cart for user
            shoppingCart = new ShoppingCartEntity();
            shoppingCart.setUser(user);
        }
        List<ProductEntity> products = shoppingCart.getProducts();
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
        shoppingCart.setProducts(products);

        return shoppingCartRepository.save(shoppingCart);
    }
}
