package com.example.pricingservice.price.infra.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceJpaRepository extends JpaRepository<JPAPrice, Long> {
Optional<JPAPrice> findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        int productId,
        int brandId,
        LocalDateTime applicableDateStart,
        LocalDateTime applicableDateEnd
);
}
