package com.group4.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private Long productID;
    private String name;
    private int price;
    private int status;
    private CategoryModel category;
    private ManufacturerModel manufacturer;
    private ProductDetailModel detail;
}
