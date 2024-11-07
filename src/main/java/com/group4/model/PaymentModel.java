package com.group4.model;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
    private Long paymentID;
    private String paymentMethod;
    private String paymentStatus;
    private Date paymentDate;
    private int total;
    private OrderModel order;

}
