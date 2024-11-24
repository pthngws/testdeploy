package com.group4.service;

import com.group4.dto.PaymentDTO;
import com.group4.entity.PaymentEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

public interface IPaymentService {
    public String generateQr(int orderId, int amount);
    public PaymentDTO createVnPayPayment(HttpServletRequest request);

    void handlePayBank(String transactionNo, String bankCode, String transactionStatus, LocalDateTime localDateTime, int amount, Long orderId);
}
