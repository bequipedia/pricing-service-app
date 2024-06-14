package com.example.pricingservice.e2eTest;

import com.example.pricingservice.domain.Price;
import com.example.pricingservice.domain.PriceRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        priceRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        priceRepository.save(new Price(null, 1, LocalDateTime.parse("2020-06-14-00.00.00", formatter), LocalDateTime.parse("2020-12-31-23.59.59", formatter), 1, 35455, 0, 35.50, "EUR"));
        priceRepository.save(new Price(null, 1, LocalDateTime.parse("2020-06-14-15.00.00", formatter), LocalDateTime.parse("2020-06-14-18.30.00", formatter), 2, 35455, 1, 25.45, "EUR"));
        priceRepository.save(new Price(null, 1, LocalDateTime.parse("2020-06-15-00.00.00", formatter), LocalDateTime.parse("2020-06-15-11.00.00", formatter), 3, 35455, 1, 30.50, "EUR"));
        priceRepository.save(new Price(null, 1, LocalDateTime.parse("2020-06-15-16.00.00", formatter), LocalDateTime.parse("2020-12-31-23.59.59", formatter), 4, 35455, 1, 38.95, "EUR"));
    }

    @DisplayName("Test getPrice with various dates")
    @ParameterizedTest(name = "{index} => applicationDate={0}, expectedPrice={1}")
    @MethodSource("priceProvider")
    public void testGetPrice(LocalDateTime applicationDate, double expectedPrice) {
        given()
                .param("applicationDate", applicationDate.toString())
                .param("productId", "35455")
                .param("brandId", "1")
                .when()
                .get("/prices")
                .then()
                .statusCode(200)
                .body("price", equalTo((float) expectedPrice));
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
