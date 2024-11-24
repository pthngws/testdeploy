package com.group4.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group4.config.ResponseObject;
import com.group4.dto.PaymentDTO;
import com.group4.dto.QRPaymentRequest;
import com.group4.entity.OrderEntity;
import com.group4.entity.PaymentEntity;
import com.group4.repository.OrderRepository;
import com.group4.repository.PaymentRepository;
import com.group4.service.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private final IPaymentService paymentService;
    //Tạo mã QR thanh toán.
    @PostMapping("/qr")
    public ResponseEntity<Map<String, Object>> generateQRPayment(@RequestBody QRPaymentRequest request) {
        String qrCode = paymentService.generateQr(request.getAmount(), request.getOrderId());
        return ResponseEntity.ok(Map.of("qrCode", qrCode));
    }

    @GetMapping("qr-callback")
    public void qrPayCallbackHandler(HttpServletRequest request){

    }

    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO> bankPay(HttpServletRequest request) {
        return new ResponseObject<  >(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public void bankPayCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        String status = request.getParameter("vnp_ResponseCode");
        String transactionNo = request.getParameter("vnp_TransactionNo");
        String bankCode = request.getParameter("vnp_BankCode");
        String transactionStatus = request.getParameter("vnp_TransactionStatus");
        int amount = Integer.parseInt(request.getParameter("vnp_Amount"))/100;

        String payDate = request.getParameter("vnp_PayDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.parse(payDate, formatter);

        Long orderId = Long.parseLong(request.getParameter("orderid"));

        if (status.equals("00")) {
            paymentService.handlePayBank(transactionNo,bankCode,transactionStatus,localDateTime,amount, orderId);
            response.getWriter().write(
                    "<script>" +
                            "alert('Đơn hàng đã thanh toán thành công');" +
                            "window.location.href = '/order-history';"+
                            "</script>"
            );
        } else {
            response.getWriter().write(
                    "<script>" +
                         "alert('Thanh toán thất bại. Vui lòng thử lại!');" + // Hiển thị popup lỗi//
                            "window.location.href = '/payment?orderId="+orderId +"&amount=" + amount +
                    "</script>"
            );
        }
    }
}

