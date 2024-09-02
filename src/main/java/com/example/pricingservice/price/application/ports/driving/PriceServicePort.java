package com.example.pricingservice.price.application.ports.driving;

import com.example.pricingservice.price.domain.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceServicePort {
    Optional<Price> findApplicablePrice(int productId, int brandId, LocalDateTime date);
}
