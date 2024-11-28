package com.group4.service;

import com.group4.dto.EmailDetail;

public interface IEmailService {
    String sendEmailConfirmCancelOrder(EmailDetail emailDetail);
    void sendEmail(String to, String subject, String message);
    public void sendInvoice(EmailDetail detail);
}
