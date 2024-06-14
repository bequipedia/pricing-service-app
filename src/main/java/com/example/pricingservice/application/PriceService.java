package com.example.pricingservice.application;

import com.example.pricingservice.domain.Price;
import com.example.pricingservice.domain.PriceRepository;
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
            return priceRepository.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                    productId, brandId, date, date);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
