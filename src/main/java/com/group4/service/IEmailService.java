package com.group4.service;

import com.group4.dto.EmailDetail;

public interface IEmailService {
    String sendEmailConfirmCancelOrder(EmailDetail emailDetail);
}
