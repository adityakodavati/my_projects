package com.system.readycrudop.controller;

import com.system.readycrudop.entity.ProductEntity;
import com.system.readycrudop.entity.Response;
import com.system.readycrudop.exception.ProductNotFoundException;
import com.system.readycrudop.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    public final IProductService service;

    @GetMapping
    public ResponseEntity<Response> geAllProducts() {

        List<ProductEntity> productEntityList = service.findAllProductsFromDb();

        Response response = new Response(productEntityList, "Product list", true);

        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable Long id) {
        java.util.Optional<ProductEntity> product = service.findProductByIdFromDb(id);

        if (product.isPresent()) {
            return ResponseEntity.ok(
                    new Response(product.get(), "Product found", true)
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(null, "Product not found!", false));
    }

    @PostMapping
    public ResponseEntity<Response> createProduct(@RequestBody ProductEntity entity)
    {
        ProductEntity savedProduct = service.addProductToDb(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response(savedProduct, "Product saved successfully", true));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProductById(@PathVariable Long id, @RequestBody ProductEntity product) throws ProductNotFoundException {
       service.checkAndUpdateProduct(id,product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateFieldById(@PathVariable Long id, @RequestBody ProductEntity entity)
    {
        service.updateField(id, entity);
    }
}