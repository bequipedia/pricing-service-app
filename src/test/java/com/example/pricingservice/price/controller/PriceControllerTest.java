package com.example.pricingservice.price.controller;

import com.example.pricingservice.price.application.PriceService;
import com.example.pricingservice.price.domain.Price;
import com.example.pricingservice.price.infra.controller.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PriceControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
    }

    @DisplayName("getPrice returns OK with various dates")
    @ParameterizedTest(name = "{index} => applicableDate={0}, expectedPrice={1}")
    @MethodSource("priceProvider")
    public void shouldReturnOkWhenCallGetPrice(LocalDateTime applicableDate, double expectedPrice) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        Price price = new Price(null, 1, LocalDateTime.parse("2020-06-14-00.00.00", formatter), LocalDateTime.parse("2020-12-31-23.59.59", formatter), 1, 35455, 0, expectedPrice, "EUR");

        when(priceService.findApplicablePrice(35455, 1, applicableDate)).thenReturn(Optional.of(price));

        mockMvc.perform(get("/prices")
                        .param("applicableDate", applicableDate.toString())
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(expectedPrice));
    }

    @DisplayName("getPrice returns 404 when not found")
    @Test
    public void shouldReturnNotFoundWhenGetPriceNotFound() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        LocalDateTime applicableDate = LocalDateTime.parse("2020-06-14-10.00.00", formatter);

        when(priceService.findApplicablePrice(35455, 1, applicableDate)).thenReturn(Optional.empty());

        mockMvc.perform(get("/prices")
                        .param("applicableDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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
