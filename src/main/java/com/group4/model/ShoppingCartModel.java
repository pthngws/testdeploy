package com.group4.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartModel {
    private Long cartID;
    private UserModel user;
    private List<ProductModel> productList;

}
