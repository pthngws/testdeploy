package com.group4.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItemModel {
    private Long id;
    private ProductModel product;
    private int quantity;
    private int total;
    private OrderModel order;


    public int getTotalAmount(){
        return total*quantity;
    }
}
