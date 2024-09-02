package com.example.pricingservice.price.application.service;

import com.example.pricingservice.price.application.PriceRepositoryAdapter;
import com.example.pricingservice.price.domain.Price;
import com.example.pricingservice.price.infra.persistence.JPAPrice;
import com.example.pricingservice.price.infra.persistence.PriceJpaRepository;
import com.example.pricingservice.price.infra.persistence.PriceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class PriceRepositoryAdapterTest {
    @Mock
    private PriceJpaRepository priceJpaRepository;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceRepositoryAdapter priceRepositoryAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnPriceWhenEntityIsFound() {
        int productId = 35455;
        int brandId = 1;
        LocalDateTime applicableDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        JPAPrice jpaPrice = new JPAPrice();
        Price expectedPrice = new Price();

        when(priceJpaRepository.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(productId, brandId, applicableDate, applicableDate))
                .thenReturn(Optional.of(jpaPrice));

        when(priceMapper.toDomain(jpaPrice)).thenReturn(expectedPrice);

        Optional<Price> result = priceRepositoryAdapter.findApplicablePrice(productId, brandId, applicableDate);

        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());
    }

    @Test
    public void shouldReturnEmptyWhenEntityIsNotFound() {
        int productId = 35455;
        int brandId = 1;
        LocalDateTime applicableDate = LocalDateTime.of(2020, 6, 17, 10, 0);

        when(priceJpaRepository.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(productId, brandId, applicableDate, applicableDate))
                .thenReturn(Optional.empty());

        Optional<Price> result = priceRepositoryAdapter.findApplicablePrice(productId, brandId, applicableDate);

        assertTrue(result.isEmpty());
    }
}
