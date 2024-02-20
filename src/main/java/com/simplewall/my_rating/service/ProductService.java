package com.simplewall.my_rating.service;

import com.simplewall.my_rating.model.entity.Category;
import com.simplewall.my_rating.model.entity.Product;
import com.simplewall.my_rating.model.entity.User;
import com.simplewall.my_rating.model.exception.RestException;
import com.simplewall.my_rating.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private UsersRepository usersRepository;

    public ResponseEntity<?> addProduct(String login, Long categoryId, Product product) {
        User user = usersRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));
        Category category = user.getCategories().stream()
                .filter(c -> c.getId() == categoryId)
                .findFirst()
                .orElse(null);
        if (category != null) {
            category.addProduct(product);
            usersRepository.save(user);
            return ResponseEntity.ok("Product added to category successfully");
        } else {
            return ResponseEntity.badRequest().body("Category not found for user");
        }
    }

    public ResponseEntity<?> updateProductName(String login, Long categoryId, Long productId, String newName) {
        User user = usersRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));

        Category category = user.getCategories().stream()
                .filter(c -> c.getId() == categoryId)
                .findFirst()
                .orElse(null);

        if (category != null) {
            Boolean updateProduct = category.updateProduct(productId, newName);
            if (updateProduct) {
                usersRepository.save(user);
                return ResponseEntity.ok("Product name updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Product not found in category");
            }
        } else {
            return ResponseEntity.badRequest().body("Category not found for user");
        }
    }

    public ResponseEntity<?> deleteProductFromCategory(String login, Long categoryId, Long productId) {
        User user = usersRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));
        Category category = user.getCategories().stream()
                .filter(c -> c.getId() == categoryId)
                .findFirst()
                .orElse(null);
        if (category != null) {
            category.removeProduct(productId);
            usersRepository.save(user);
            return ResponseEntity.ok("Product deleted from category successfully");
        } else {
            return ResponseEntity.badRequest().body("Category not found for user");
        }
    }
}
