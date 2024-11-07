package com.group4.model;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionModel {
    private Long promotionID;
    private int discountAmount;
    private Date validFrom;
    private Date validTo;
    private String promotionCode;
    private String description;
}
