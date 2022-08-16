package com.example.app.controller;

import com.example.app.dtos.Voucher;
import com.example.app.dtos.VoucherRequestDTO;
import com.example.app.dtos.VoucherResponse;
import com.example.app.services.MyntServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

    @Autowired
    private MyntServices myntServices;

    @PostMapping(value = "/vouchers")
    public VoucherResponse getPricePath(@RequestBody VoucherRequestDTO voucherRequestDTO) throws Exception {

        String voucherCode = voucherRequestDTO.getVoucherCode();
        double price = getPrice(voucherRequestDTO);

        double discount = myntServices.getDiscount(voucherCode);
        double priceWithDiscount = price - discount;

        return VoucherResponse
                .builder()
                    .originalPrice(price)
                     .discount(discount)
                     .finalPrice(priceWithDiscount < 0 ? 0 : priceWithDiscount)
                .build();
    }

    double getPrice(VoucherRequestDTO voucherRequestDTO) throws Exception {

        double weight = voucherRequestDTO.getWeight();
        double height = voucherRequestDTO.getHeight();
        double width = voucherRequestDTO.getWidth();
        double length = voucherRequestDTO.getLength();

        if (weight > 50) {
            throw new Exception("Invalid Weight!");
        }

        if (weight <= 50 && weight > 10) {
            return 20 * weight;
        }

        double volume = length * width * height;

        if (weight <= 10 && volume >= 2500) {
            return 0.05 * volume;
        }

        if (volume >=1500 && volume < 2500) {
            return 0.04 * volume;
        }

        if (volume < 1500) {
            return 0.03 * volume;
        }

        return 0;
    }
}
