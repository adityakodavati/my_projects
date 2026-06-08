package com.system.readycrudop.service;

import com.system.readycrudop.entity.ProductEntity;
import com.system.readycrudop.exception.ProductNotFoundException;
import com.system.readycrudop.repository.IProductRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    @Autowired
    private final IProductRepository repository;

    public List<ProductEntity> findAllProductsFromDb() {
        return repository.findAll();
    }

    @Override
    public Optional<ProductEntity> findProductByIdFromDb(Long id) {
        return repository.findById(id);
    }

    @Override
    public ProductEntity addProductToDb(ProductEntity entity) {
       return repository.save(entity);
    }

    @Override
    public void checkAndUpdateProduct(Long id, ProductEntity product) throws ProductNotFoundException {

        ProductEntity foundProduct = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found with id :" + id));
        foundProduct.setProductName(product.getProductName());
        foundProduct.setDescription(product.getDescription());
        foundProduct.setPrice(product.getPrice());

        repository.save(foundProduct);
    }

    @Override
    public void updateField(Long id, ProductEntity product) throws ProductNotFoundException {
      ProductEntity foundProduct =  repository.findById(id).orElseThrow(()-> new ProductNotFoundException("No product found based on id :" + id));
      foundProduct.setPrice(product.getPrice());
      foundProduct.setProductName(product.getProductName());
      foundProduct.setDescription(product.getDescription());
      repository.save(foundProduct);
    }


}
