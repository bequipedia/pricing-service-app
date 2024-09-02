package com.example.pricingservice.price.application;

import com.example.pricingservice.price.domain.Price;
import com.example.pricingservice.price.application.ports.driven.PriceRepositoryPort;
import com.example.pricingservice.price.infra.persistence.PriceJpaRepository;
import com.example.pricingservice.price.infra.persistence.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository priceJpaRepository;
    private final PriceMapper priceMapper;
    @Override
    public Optional<Price> findApplicablePrice(int productId, int brandId, LocalDateTime applicableDate) {

        return priceJpaRepository.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId,
                brandId,
                applicableDate,
                applicableDate
        ).map(priceMapper::toDomain);
    }
}
