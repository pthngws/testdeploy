package com.group4.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderModel {
    private Long orderId;
    private UserModel user;
    private AddressModel shippingAddress;
    private Date orderDate;
    private Date receiveDate;
    //Trạng thái giao hàng
    private String shippingStatus;
    //Phương thức thanh toán
    private String paymentMethod;
    //Trạng thái thanh toán
    private String paymentStatus;
    // Phương thức giao hàng
    private String shippingMethod;
    private String phoneNumber;
    private String note;
    private int totalPrice;
    private List<LineItemModel> listLineItems;
}

