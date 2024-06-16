package com.example.pricingservice.price.infra.controller;

import com.example.pricingservice.price.application.PriceService;
import com.example.pricingservice.price.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class PriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping("/prices")
    public ResponseEntity<Price> getPrice(
            @RequestParam("applicableDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicableDate,
            @RequestParam("productId") int productId,
            @RequestParam("brandId") int brandId) {

        Optional<Price> price = priceService.findApplicablePrice(productId, brandId, applicableDate);
        return price.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
