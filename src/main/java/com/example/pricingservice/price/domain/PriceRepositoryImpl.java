package com.example.pricingservice.price.domain;

import com.example.pricingservice.price.infra.persistence.PriceEntity;
import com.example.pricingservice.price.infra.persistence.PriceMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class PriceRepositoryImpl implements PriceRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<Price> findApplicablePrice(int productId, int brandId, LocalDateTime applicationDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PriceEntity> criteriaQuery = criteriaBuilder.createQuery(PriceEntity.class);

        Root<PriceEntity> price = criteriaQuery.from(PriceEntity.class);

        Predicate productIdPredicate = criteriaBuilder.equal(price.get("productId"), productId);
        Predicate brandIdPredicate = criteriaBuilder.equal(price.get("brandId"), brandId);
        Predicate startDatePredicate = criteriaBuilder.lessThanOrEqualTo(price.get("startDate"), applicationDate);
        Predicate endDatePredicate = criteriaBuilder.greaterThanOrEqualTo(price.get("endDate"), applicationDate);

        criteriaQuery.where(criteriaBuilder.and(productIdPredicate, brandIdPredicate, startDatePredicate, endDatePredicate));
        criteriaQuery.orderBy(criteriaBuilder.desc(price.get("priority")));

        TypedQuery<PriceEntity> query = entityManager.createQuery(criteriaQuery);
        List<PriceEntity> result = query.setMaxResults(1).getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(PriceMapper.toDomain(result.get(0)));
    }
}
