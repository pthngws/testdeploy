package com.group4.model;


import com.group4.entity.CategoryEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {
    private Long categoryID;
    private String name;
    private String description;

    public CategoryModel(CategoryEntity category) {
        this.categoryID = category.getCategoryID();
        this.name = category.getName();
        this.description = category.getDescription();
    }
}


