package com.simplewall.my_rating.controller;

import com.simplewall.my_rating.model.entity.Product;
import com.simplewall.my_rating.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestParam String login,
            @RequestParam Long categoryId,
            @RequestBody Product product
    ) {
        return productService.addProduct(login, categoryId, product);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProductName(
            @RequestParam String login,
            @RequestParam Long categoryId,
            @RequestParam Long productId,
            @RequestParam String newName
    ) {
        return productService.updateProductName(login, categoryId, productId, newName);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProductFromCategory(
            @RequestParam String login,
            @RequestParam Long categoryId,
            @RequestParam Long productId
    ) {
        return productService.deleteProductFromCategory(login, categoryId, productId);
    }
}
