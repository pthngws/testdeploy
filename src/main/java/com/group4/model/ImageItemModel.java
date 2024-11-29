package com.group4.model;

import com.group4.entity.ImageItemEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageItemModel {
    private Long id;
    private String name;
    private String imageUrl;
    private ProductDetailModel productDetail;

    public ImageItemModel(ImageItemEntity image) {
        this.id = image.getId();
        this.name = image.getName();
        this.imageUrl = image.getImageUrl();
    }

}