package com.example.pricingservice.price.application;

import com.example.pricingservice.price.domain.Price;
import com.example.pricingservice.price.domain.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;

    public Optional<Price> findApplicablePrice(int productId, int brandId, LocalDateTime date) {
        try {
            return priceRepository.findApplicablePrice(productId, brandId, date);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
