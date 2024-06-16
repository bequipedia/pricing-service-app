package com.example.pricingservice.price.IntTest;

import com.example.pricingservice.price.infra.persistence.PriceRepository;
import com.example.pricingservice.price.infra.persistence.JPAPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIntTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    public void setUp() {
        priceRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
    priceRepository.save(
        new JPAPrice(
            null,
            1,
            LocalDateTime.parse("2020-06-14-00.00.00", formatter),
            LocalDateTime.parse("2020-12-31-23.59.59", formatter),
            1,
            35455,
            0,
            35.50,
            "EUR"));
        priceRepository.save(new JPAPrice(null, 1, LocalDateTime.parse("2020-06-14-15.00.00", formatter), LocalDateTime.parse("2020-06-14-18.30.00", formatter), 2, 35455, 1, 25.45, "EUR"));
        priceRepository.save(new JPAPrice(null, 1, LocalDateTime.parse("2020-06-15-00.00.00", formatter), LocalDateTime.parse("2020-06-15-11.00.00", formatter), 3, 35455, 1, 30.50, "EUR"));
        priceRepository.save(new JPAPrice(null, 1, LocalDateTime.parse("2020-06-15-16.00.00", formatter), LocalDateTime.parse("2020-12-31-23.59.59", formatter), 4, 35455, 1, 38.95, "EUR"));
    }

    @DisplayName("shouldGetPrice with various dates")
    @ParameterizedTest(name = "{index} => applicationDate={0}, expectedPrice={1}")
    @MethodSource("priceProvider")
    public void shouldGetPrice(LocalDateTime applicationDate, double expectedPrice) throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", applicationDate.toString())
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(expectedPrice));
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
