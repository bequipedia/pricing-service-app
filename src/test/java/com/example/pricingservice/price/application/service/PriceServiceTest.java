package com.example.pricingservice.price.application.service;

import com.example.pricingservice.price.application.PriceService;
import com.example.pricingservice.price.application.ports.driven.PriceRepositoryPort;
import com.example.pricingservice.price.domain.Price;
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

public class PriceServiceTest {
    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnPriceWhenFound() {
        int productId = 35455;
        int brandId = 1;
        LocalDateTime applicableDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Price expectedPrice = new Price();

        when(priceRepositoryPort.findApplicablePrice(productId, brandId, applicableDate))
                .thenReturn(Optional.of(expectedPrice));

        Optional<Price> result = priceService.findApplicablePrice(productId, brandId, applicableDate);

        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());
    }

    @Test
    public void shouldReturnEmptyWhenPriceNotFound() {
        int productId = 35455;
        int brandId = 1;
        LocalDateTime applicableDate = LocalDateTime.of(2020, 6, 17, 10, 0);

        when(priceRepositoryPort.findApplicablePrice(productId, brandId, applicableDate))
                .thenReturn(Optional.empty());

        Optional<Price> result = priceService.findApplicablePrice(productId, brandId, applicableDate);

        assertTrue(result.isEmpty());
    }
}
