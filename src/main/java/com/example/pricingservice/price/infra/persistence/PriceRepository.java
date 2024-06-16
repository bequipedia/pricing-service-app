package com.example.pricingservice.price.infra.persistence;


import com.example.pricingservice.price.domain.PriceRepositoryCustom;
import com.example.pricingservice.price.infra.persistence.JPAPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<JPAPrice, Long>, PriceRepositoryCustom {

}
