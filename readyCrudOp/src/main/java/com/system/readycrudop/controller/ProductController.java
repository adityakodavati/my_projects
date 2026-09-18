package com.system.readycrudop.controller;

import com.system.readycrudop.entity.ProductEntity;
import com.system.readycrudop.entity.Response;
import com.system.readycrudop.exception.ProductNotFoundException;
import com.system.readycrudop.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {


    public final IProductService service;

    @GetMapping
    public ResponseEntity<Response<List<ProductEntity>, String, Boolean>> geAllProducts() {

        List<ProductEntity> productEntityList = service.findAllProductsFromDb();

        Response<List<ProductEntity>, String, Boolean> response = new Response<>(productEntityList, "Product list", true);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductEntity, String, Boolean>> getProductById(@PathVariable Long id) {

        java.util.Optional<ProductEntity> product = service.findProductByIdFromDb(id);
        /*      return product.map(product1 -> ResponseEntity.ok(new Response<>(product1, "Product found", true))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, "Product not found!", false)));*/
        if (product.isPresent()) {
            Response<ProductEntity, String, Boolean> response = new Response<>(product.get(), "Product id", false);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, "Product id", false));

    }

    @PostMapping
    public ResponseEntity<Response<ProductEntity, String, Boolean>> createProduct(@RequestBody ProductEntity entity) {
        ProductEntity savedProduct = service.addProductToDb(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(savedProduct, "Product saved successfully", true));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ProductEntity, String, Boolean>> updateProductById(@PathVariable Long id, @RequestBody ProductEntity product) throws ProductNotFoundException {
        service.checkAndUpdateProduct(id, product);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(product, "Product Updated Successfully", true));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<ProductEntity, String, Boolean>> updateFieldById(@PathVariable Long id, @RequestBody ProductEntity product) throws ProductNotFoundException {
        service.updateField(id, product);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(product, "Field Updated Successfully", true));
    }
}