package com.example.pricingservice.price.domain;


import com.example.pricingservice.price.infra.persistence.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long>, PriceRepositoryCustom {

}
