package com.example.pricingservice.price.infra.persistence;

import com.example.pricingservice.price.domain.Price;

public class PriceMapper {
    public static Price toDomain(PriceEntity entity) {
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

    public static PriceEntity toEntity(Price domain) {
        return new PriceEntity(
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
