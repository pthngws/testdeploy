package com.group4.restController;

import com.group4.dto.EmailDetail;
import com.group4.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class EmailController {
    @Autowired
    private IEmailService emailService;

    @PostMapping("/cancelOrder")
    public String sendMailCancelOrder(@RequestBody EmailDetail detail)
    {
        String status = emailService.sendEmailConfirmCancelOrder(detail);
        return status;
    }
}

