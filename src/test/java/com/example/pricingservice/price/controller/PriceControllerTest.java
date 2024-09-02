package com.example.pricingservice.price.controller;

import com.example.pricingservice.price.application.ports.driving.PriceServicePort;
import com.example.pricingservice.price.domain.Price;
import com.example.pricingservice.price.infra.controllers.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PriceControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PriceServicePort priceService;

    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
    }

    @Test
    public void shouldReturnOkWhenPriceIsFound() throws Exception {
        LocalDateTime applicableDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Price price = new Price();
        when(priceService.findApplicablePrice(35455, 1, applicableDate)).thenReturn(Optional.of(price));

        mockMvc.perform(get("/prices")
                        .param("applicableDate", applicableDate.toString())
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").exists());
    }

    @Test
    public void shouldReturnNotFoundWhenPriceIsNotFound() throws Exception {
        LocalDateTime applicableDate = LocalDateTime.of(2020, 6, 17, 10, 0);
        when(priceService.findApplicablePrice(35455, 1, applicableDate)).thenReturn(Optional.empty());

        mockMvc.perform(get("/prices")
                        .param("applicableDate", applicableDate.toString())
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
