package com.system.readycrudop.controller;

import com.system.readycrudop.entity.ProductEntity;
import com.system.readycrudop.entity.Response;
import com.system.readycrudop.exception.ProductNotFoundException;
import com.system.readycrudop.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @GetMapping("/page")
    public ResponseEntity<Response<List<ProductEntity>,String, Boolean>>getProductsByPage(@RequestParam(defaultValue = "0", required = false) int pageNo, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "id") String sortBy, @RequestParam(required = false, defaultValue = "asc") String sortDir, @RequestParam(required = false)String search)
    {
        System.out.println(sortBy + "===" + sortDir);
        Sort sort;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }
        else if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        }
        else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
        Page<ProductEntity> products = service.getProductsInPages(search,pageable);
        return ResponseEntity.status(HttpStatus.OK).body((new Response<>(products.getContent(), "Success", true)));
    }

    @PostMapping
    public ResponseEntity<Response<ProductEntity, String, Boolean>> createProduct(@RequestBody ProductEntity entity) {
        ProductEntity savedProduct = service.addProductToDb(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(savedProduct, "Product saved successfully", true));

    }

    @PostMapping("/productList")
    public List<ProductEntity> createProducts(
            @RequestBody List<ProductEntity> products) {

        return service.createProducts(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ProductEntity, String, Boolean>> updateProductById(@PathVariable Long id, @RequestBody ProductEntity product) throws ProductNotFoundException {
        service.checkAndUpdateProduct(id, product);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(product, "Product Updated Successfully", true));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<ProductEntity, String, Boolean>> updateFieldById(@PathVariable Long id, @RequestBody ProductEntity product) throws  ProductNotFoundException {
        service.updateField(id, product);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(product, "Field Updated Successfully", true));
    }
}