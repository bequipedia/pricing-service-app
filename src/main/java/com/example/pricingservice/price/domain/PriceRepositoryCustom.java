package com.example.pricingservice.price.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryCustom {
    Optional<Price> findApplicablePrice(int productId, int brandId, LocalDateTime applicableDate);
}
