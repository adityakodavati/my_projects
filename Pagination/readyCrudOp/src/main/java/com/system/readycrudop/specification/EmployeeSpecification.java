package com.system.readycrudop.specification;

import com.system.readycrudop.entity.ProductEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeSpecification {
    public static Specification<ProductEntity> getSpecification(String search) {
        return new Specification<ProductEntity>() {

            @Override
            public @Nullable Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if (search == null || search.isEmpty())  {
                    return  criteriaBuilder.conjunction();
                }
                else{
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.like(root.get("price").as(String.class), "%" + search + "%"));
                    predicates.add(criteriaBuilder.like(root.get("productName"), "%" + search + "%"));

                    return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
                }
            }
        };
    }
}
