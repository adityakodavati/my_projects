package com.system.readycrudop.service;

import com.system.readycrudop.dto.ProductMapper;
import com.system.readycrudop.dto.ProductRequestDto;
import com.system.readycrudop.dto.ProductResponseDto;
import com.system.readycrudop.entity.ProductEntity;
import com.system.readycrudop.exception.ProductNotFoundException;
import com.system.readycrudop.repository.IProductRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    @Autowired
    private final IProductRepository repository;

    @Autowired
    ProductMapper productMapper;

    public List<ProductResponseDto> findAllProductsFromDb() {
        return productMapper.toProductResponsedtoList(repository.findAll());
    }

    @Override
    public ProductResponseDto findProductByIdFromDb(Long id) {

        Optional<ProductEntity> product = repository.findById(id);
        ProductEntity productEntity = product.orElse(new ProductEntity());
        return productMapper.toProductResponsedto(productEntity);
    }

    @Override
    public ProductEntity addProductToDb(ProductRequestDto requestDto) {
        ProductEntity entity = productMapper.toProductEntity(requestDto);
        return repository.save(entity);
    }

    @Override
    public void checkAndUpdateProduct(Long id, ProductRequestDto requestDto) throws ProductNotFoundException {

        ProductEntity foundProduct = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found with id :" + id));
        foundProduct.setProductName(requestDto.getName());
        foundProduct.setDescription(requestDto.getDescription());
        foundProduct.setPrice(requestDto.getPrice());

        repository.save(foundProduct);
    }

    @Override
    public ProductResponseDto updateField(Long id, Map<String, Object> updates) throws ProductNotFoundException {
        ProductEntity foundProduct = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("No product found based on id :" + id));

       updates.forEach((key, value) -> {
            switch (key) {

                case "name":
                    foundProduct.setProductName((String) value);
                    break;

                case "description":
                    foundProduct.setDescription((String)value);
                    break;

                case "price":
                    foundProduct.setPrice((BigDecimal) value);
                    break;
            }
        });
       return productMapper.toProductResponsedto(repository.save(foundProduct));
    }


}
