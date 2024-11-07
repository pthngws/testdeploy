package com.group4.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateModel {
    private Long rateID;
    private UserModel user;
    private ProductModel product;
    private String content;
    private int rate;

}
