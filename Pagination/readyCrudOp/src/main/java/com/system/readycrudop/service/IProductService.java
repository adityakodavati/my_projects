package com.system.readycrudop.service;

import com.system.readycrudop.entity.ProductEntity;
import com.system.readycrudop.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;


public interface IProductService {
    List<ProductEntity> findAllProductsFromDb();

    Optional<ProductEntity> findProductByIdFromDb(Long id);

    ProductEntity addProductToDb(ProductEntity entity);


    void checkAndUpdateProduct(Long id, ProductEntity product) throws ProductNotFoundException;

    void updateField(Long id, ProductEntity product) throws ProductNotFoundException;

    List<ProductEntity> createProducts(List<ProductEntity> products);

    Page<ProductEntity> getProductsInPages(String search, Pageable pageable);
}
