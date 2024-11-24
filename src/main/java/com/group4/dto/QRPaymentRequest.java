package com.group4.dto;

import lombok.Data;

@Data
public class QRPaymentRequest {
    private int amount;
    private int orderId;
}

