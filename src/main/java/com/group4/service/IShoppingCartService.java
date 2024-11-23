package com.group4.service;

import com.group4.entity.ProductEntity;
import com.group4.entity.ShoppingCartEntity;
import com.group4.entity.UserEntity;

public interface IShoppingCartService {

    ShoppingCartEntity addProductToCart(UserEntity user, ProductEntity product);
}
