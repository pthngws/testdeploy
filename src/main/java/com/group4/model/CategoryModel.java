package com.group4.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {
    private Long categoryID;
    private String name;
    private String description;
}
