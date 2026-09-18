package com.system.readycrudop.controller;

import com.system.readycrudop.dto.ProductRequestDto;
import com.system.readycrudop.dto.ProductResponseDto;
import com.system.readycrudop.entity.ProductEntity;
import com.system.readycrudop.entity.Response;
import com.system.readycrudop.exception.ProductNotFoundException;
import com.system.readycrudop.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {


    public final IProductService service;

    public ProductController(IProductService productService) {
        this.service = productService;
    }

    @GetMapping
    public ResponseEntity<Response> geAllProducts() {

        List<ProductResponseDto> productResponseDtos = service.findAllProductsFromDb();

        Response response = new Response(productResponseDtos, "Product list", true);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable Long id) {
        ProductResponseDto product = service.findProductByIdFromDb(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(product, "Product not found!", false));
    }

    @PostMapping
    public ResponseEntity<Response> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        ProductEntity savedProduct = service.addProductToDb(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response(savedProduct, "Product saved successfully", true));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProductById(@PathVariable Long id, @RequestBody ProductRequestDto requestDto) throws ProductNotFoundException {
        service.checkAndUpdateProduct(id, requestDto);
        Response<ProductEntity> productEntityResponse = new Response<>(null, "Product updation successful!", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productEntityResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateFieldById(@PathVariable Long id, @Valid @RequestBody ProductRequestDto requestDto) throws ProductNotFoundException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = requestDto.getClass().getDeclaredFields();

        for (Field field : fields) {
            // field.setAccessible(true); // Allows access to private fields
            try {
                map.put(field.getName(), field.get(field));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ProductResponseDto productResponseDto = service.updateField(id, map);
        Response<ProductResponseDto> response = new Response<>(productResponseDto, "Product updated Successfully!", true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}