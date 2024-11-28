package com.group4.service;

import com.group4.dto.PaymentDTO;
import com.group4.entity.PaymentEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IPaymentService {
    public String generateQr(int orderId, int amount);
    public PaymentDTO createVnPayPayment(HttpServletRequest request);

    void handlePayBank(String transactionNo, String bankCode, String transactionStatus, LocalDateTime localDateTime, int amount, Long orderId);
    void handlePayQr(Long orderId, int amount);
    Double getDailyRevenue(LocalDate date);
    List<Map<String, Object>> getYearlyRevenue(int year);

}
