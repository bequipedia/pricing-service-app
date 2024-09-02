package com.example.pricingservice.price.application;

import com.example.pricingservice.price.domain.Price;
import com.example.pricingservice.price.application.ports.driven.PriceRepositoryPort;
import com.example.pricingservice.price.application.ports.driving.PriceServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceService implements PriceServicePort {

    private final PriceRepositoryPort priceRepositoryPort;
    public Optional<Price> findApplicablePrice(int productId, int brandId, LocalDateTime date) {
        return priceRepositoryPort.findApplicablePrice(productId, brandId, date);
    }
}
