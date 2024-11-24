package com.group4.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class VietQRConfig {
    @Getter
    @Value("${payment.vietQr.url}")
    private String vietQr_PayUrl;

    @Getter
    @Value("${payment.vietQr.apiKey}")
    private String vietQr_apiKey;

    @Getter
    @Value("${payment.vietQr.clientKey}")
    private String vietQr_clientKey;

    @Getter
    @Value("${payment.vietQr.accountNumber}")
    private String vietQr_accountNumber;

    @Getter
    @Value("${payment.vietQr.accountName}")
    private String vietQr_accountName;

    @Getter
    @Value("${payment.vietQr.bankCode}")
    private String vietQr_bankCode;

    public Map<String,String> getVietQRConfig(){
        Map<String,String> vietQRparams = new HashMap<>();
        vietQRparams.put("payUrl",vietQr_PayUrl);
        vietQRparams.put("apiKey",vietQr_apiKey);
        vietQRparams.put("clientKey",vietQr_clientKey);
        vietQRparams.put("accountNumber",vietQr_accountNumber);
        vietQRparams.put("accountName",vietQr_accountName);
        vietQRparams.put("bankCode",vietQr_bankCode);
        return vietQRparams;
    }
}
