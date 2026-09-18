package com.system.readycrudop.service;

import com.system.readycrudop.dto.ProductRequestDto;
import com.system.readycrudop.dto.ProductResponseDto;
import com.system.readycrudop.entity.ProductEntity;
import com.system.readycrudop.exception.ProductNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProductService {
    List<ProductResponseDto> findAllProductsFromDb();

    ProductResponseDto findProductByIdFromDb(Long id);

    ProductEntity addProductToDb(ProductRequestDto requestDto);


    void checkAndUpdateProduct(Long id, ProductRequestDto product) throws ProductNotFoundException;

    ProductResponseDto updateField(Long id, Map<String, Object> updates) throws ProductNotFoundException;
}
