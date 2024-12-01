package com.group4.service;

import com.group4.entity.CustomerEntity;
import com.group4.entity.ProductEntity;
import com.group4.entity.ShoppingCartEntity;
import com.group4.entity.UserEntity;

import java.util.List;

public interface IShoppingCartService {

    ShoppingCartEntity addProductToCart(CustomerEntity customer, ProductEntity product);
    List<ProductEntity> findProductsByCustomerId(Long customerID);
    void removeProductFromCart(Long customerId, Long productId);
}
