package com.example.pricingservice.price.infra.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JPAPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_id", nullable = false)
    private int brandId;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "price_list", nullable = false)
    private int priceList;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "curr", nullable = false)
    private String currency;
}
