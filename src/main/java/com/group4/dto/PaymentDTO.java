package com.group4.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    public String code;
    public String message;
    public String paymentUrl;
}
