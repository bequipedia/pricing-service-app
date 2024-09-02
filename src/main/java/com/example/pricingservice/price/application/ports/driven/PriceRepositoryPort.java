package com.example.pricingservice.price.application.ports.driven;

import com.example.pricingservice.price.domain.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {
    Optional<Price> findApplicablePrice(int productId, int brandId, LocalDateTime applicableDate);
}
