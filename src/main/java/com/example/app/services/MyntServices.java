package com.example.app.services;

import com.example.app.dtos.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyntServices {

    @Autowired
    private RestTemplate restTemplate;

    public double getDiscount(String voucherCode) {
        try {
            Voucher voucher = restTemplate.getForObject("https://mynt-exam.mocklab.io/voucher/" + voucherCode + "?key=apikey", Voucher.class);
            return voucher == null ? 0 :voucher.getDiscount() ;
        } catch (Exception ex) {
            return 0;
        }
    }
}
