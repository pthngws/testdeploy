package com.group4.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageItemModel {
    private Long id;
    private String name;
    private String imageUrl;
    private ProductDetailModel productDetail;

}