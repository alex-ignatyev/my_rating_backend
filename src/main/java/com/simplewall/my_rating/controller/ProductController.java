package com.simplewall.my_rating.controller;

import com.simplewall.my_rating.model.request.product.AddProductRequest;
import com.simplewall.my_rating.model.request.product.DeleteProductRequest;
import com.simplewall.my_rating.model.request.product.UpdateProductRequest;
import com.simplewall.my_rating.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestParam String login,
            @RequestBody AddProductRequest addProductRequest
    ) {
        return productService.addProduct(login, addProductRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(
            @RequestParam String login,
            @RequestBody UpdateProductRequest updateProductRequest
    ) {
        return productService.updateProduct(login, updateProductRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(
            @RequestParam String login,
            @RequestBody DeleteProductRequest deleteProductRequest
    ) {
        return productService.deleteProduct(login, deleteProductRequest);
    }
}
