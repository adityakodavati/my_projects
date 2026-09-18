package com.system.readycrudop.exception;

import com.system.readycrudop.entity.ProductEntity;
import com.system.readycrudop.entity.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response<ProductEntity,String, Boolean>> handleProductNotFoundEx(ProductNotFoundException ex)
    {
        Response<ProductEntity, String, Boolean> response = new Response<>(null, ex.getMessage(),false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
