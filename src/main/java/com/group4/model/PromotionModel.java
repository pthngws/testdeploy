package com.group4.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionModel {
    // ID của khuyến mãi
    @NotNull(message = "Promotion ID cannot be null")
    private Long promotionID;

    // Mức giảm giá, phải nằm trong khoảng 1% - 100%
    @Min(value = 1, message = "Discount amount must be at least 1%")
    @Max(value = 100, message = "Discount amount cannot exceed 100%")
    private int discountAmount;
    private int remainingUses;
    // Ngày bắt đầu của khuyến mãi, không được null
    @NotNull(message = "Valid from date cannot be null")
    @FutureOrPresent(message = "Valid from date must be today or in the future")
    private Date validFrom;

    // Ngày kết thúc của khuyến mãi, không được null
    @NotNull(message = "Valid to date cannot be null")
    @Future(message = "Valid to date must be in the future")
    private Date validTo;

    // Mã khuyến mãi, phải có độ dài từ 3 đến 20 ký tự
    @NotBlank(message = "Promotion code cannot be blank")
    @Size(min = 3, max = 20, message = "Promotion code must be between 3 and 20 characters")
    private String promotionCode;

    // Mô tả khuyến mãi, không bắt buộc nhưng không vượt quá 500 ký tự
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
