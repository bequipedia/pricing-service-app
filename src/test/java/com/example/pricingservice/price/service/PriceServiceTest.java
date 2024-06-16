package com.example.pricingservice.price.service;

import com.example.pricingservice.price.application.PriceService;
import com.example.pricingservice.price.domain.Price;
import com.example.pricingservice.price.domain.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class PriceServiceTest {
    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("findApplicablePrice returns OK with various dates")
    @ParameterizedTest(name = "{index} => applicableDate={0}, expectedPrice={1}")
    @MethodSource("priceProvider")
    public void testFindApplicablePrice(LocalDateTime applicableDate, double expectedPrice) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        Price price = new Price(null, 1, LocalDateTime.parse("2020-06-14-00.00.00", formatter), LocalDateTime.parse("2020-12-31-23.59.59", formatter), 1, 35455, 0, expectedPrice, "EUR");

        when(priceRepository.findApplicablePrice(35455, 1, applicableDate))
                .thenReturn(Optional.of(price));

        Optional<Price> result = priceService.findApplicablePrice(35455, 1, applicableDate);
        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get().getPrice());
    }

    @Test
    public void shouldCatchExceptionWhenNotFound() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        LocalDateTime applicableDate = LocalDateTime.parse("2020-06-14-10.00.00", formatter);

        when(priceRepository.findApplicablePrice(35455, 1, applicableDate))
                .thenThrow(new RuntimeException("DB error"));

        Optional<Price> result = priceService.findApplicablePrice(35455, 1, applicableDate);
        assertTrue(result.isEmpty());
    }

    static Stream<Object[]> priceProvider() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return Stream.of(
                new Object[]{LocalDateTime.parse("2020-06-14-10.00.00", formatter), 35.50},
                new Object[]{LocalDateTime.parse("2020-06-14-16.00.00", formatter), 25.45},
                new Object[]{LocalDateTime.parse("2020-06-14-21.00.00", formatter), 35.50},
                new Object[]{LocalDateTime.parse("2020-06-15-10.00.00", formatter), 30.50},
                new Object[]{LocalDateTime.parse("2020-06-16-21.00.00", formatter), 38.95}
        );
    }
}
