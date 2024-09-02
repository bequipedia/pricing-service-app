package com.example.pricingservice.price.infra.persistence;

import com.example.pricingservice.price.domain.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    public Price toDomain(JPAPrice entity) {
        return new Price(
                entity.getId(),
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }

    public JPAPrice toEntity(Price domain) {
        return new JPAPrice(
                domain.getId(),
                domain.getBrandId(),
                domain.getStartDate(),
                domain.getEndDate(),
                domain.getPriceList(),
                domain.getProductId(),
                domain.getPriority(),
                domain.getPrice(),
                domain.getCurrency()
        );
    }
}
