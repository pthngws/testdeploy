package com.group4.model;

import com.group4.entity.ProductEntity;
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
    private double averageRating;
    private int reviewCount;

    public ProductModel(Long productID, String name, int price, int status) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public ProductModel(ProductEntity product) {
        this.productID = product.getProductID();
        this.name = product.getName();
        this.price = product.getPrice();
        this.status = product.getStatus();
        if (product.getCategory() != null) {
            this.category = new CategoryModel(product.getCategory());
        }

        if (product.getManufacturer() != null) {
            this.manufacturer = new ManufacturerModel(product.getManufacturer());
        }

        if (product.getDetail() != null) {
            this.detail = new ProductDetailModel(product.getDetail());
        }
    }

}
