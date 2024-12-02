package com.group4.service.impl;
import java.io.File;

import com.group4.dto.EmailDetail;
import com.group4.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendEmailConfirmCancelOrder(EmailDetail detail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(detail.getRecipient());
            helper.setSubject(detail.getSubject());
            helper.setText(detail.getMsgBody());

            // Kiểm tra và thêm tệp đính kèm (nếu có)
            if (detail.getAttachment() != null && !detail.getAttachment().isEmpty()) {
                File attachment = new File(detail.getAttachment());
                helper.addAttachment(attachment.getName(), attachment);
            }

            mailSender.send(message);
            return "Email thông báo hủy đơn hàng đã được gửi thành công!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Lỗi khi gửi email thông báo hủy đơn hàng!";
        }
    }
    @Override
    public void sendInvoice(EmailDetail detail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("buiducthang13022004@gmail.com");
        simpleMailMessage.setTo(detail.getRecipient());
        simpleMailMessage.setSubject(detail.getSubject());
        simpleMailMessage.setText(detail.getMsgBody());

        this.mailSender.send(simpleMailMessage);
    }


    @Override
    public void sendEmail(String to, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("buiducthang13022004@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        this.mailSender.send(simpleMailMessage);
    }
}

