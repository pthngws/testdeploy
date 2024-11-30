package com.group4.model;

import com.group4.entity.ManufacturerEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerModel {
    private Long id;
    private String name;
    private String address;
    private List<ProductModel> products;

    public ManufacturerModel(ManufacturerEntity manufacturer) {
        this.id = manufacturer.getId();
        this.name = manufacturer.getName();
        this.address = manufacturer.getAddress();
    }

}
