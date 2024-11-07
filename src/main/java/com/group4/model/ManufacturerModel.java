package com.group4.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerModel {
    private Long id;
    private String name;
    private String address;
    private List<ProductModel> products;

}
