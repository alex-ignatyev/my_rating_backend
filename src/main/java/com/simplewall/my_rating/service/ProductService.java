package com.simplewall.my_rating.service;

import com.simplewall.my_rating.model.entity.Category;
import com.simplewall.my_rating.model.entity.User;
import com.simplewall.my_rating.model.exception.RestException;
import com.simplewall.my_rating.model.request.product.AddProductRequest;
import com.simplewall.my_rating.model.request.product.DeleteProductRequest;
import com.simplewall.my_rating.model.request.product.UpdateProductRequest;
import com.simplewall.my_rating.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> addProduct(String login, AddProductRequest request) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));

        Category category = user.getCategories().stream()
                .filter(c -> c.getId() == request.getCategoryId())
                .findFirst()
                .orElseThrow(() -> new RestException("Category not found", HttpStatus.CONFLICT));

        Boolean isProductAdded = category.addProduct(request.getName(), request.getRate());
        if (isProductAdded) {
            userRepository.save(user);
            return ResponseEntity.ok("Product added to category successfully");
        } else {
            return ResponseEntity.badRequest().body("Can't add product");
        }
    }

    @Transactional
    public ResponseEntity<?> updateProduct(String login, UpdateProductRequest request) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));

        Category category = user.getCategories().stream()
                .filter(c -> c.getId() == request.getCategoryId())
                .findFirst()
                .orElseThrow(() -> new RestException("Category not found", HttpStatus.CONFLICT));

        Boolean updateProduct = category.updateProduct(request.getProductId(), request.getNewName());
        if (updateProduct) {
            userRepository.save(user);
            return ResponseEntity.ok("Product name updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Product not found");
        }
    }

    @Transactional
    public ResponseEntity<?> deleteProduct(String login, DeleteProductRequest request) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));

        Category category = user.getCategories().stream()
                .filter(c -> c.getId() == request.getCategoryId())
                .findFirst()
                .orElseThrow(() -> new RestException("Category not found", HttpStatus.CONFLICT));

        Boolean isProductDeleted = category.removeProduct(request.getProductId());
        if (isProductDeleted) {
            userRepository.save(user);
            return ResponseEntity.ok("Product deleted from category successfully");
        } else {
            return ResponseEntity.badRequest().body("Product not found");
        }
    }
}
