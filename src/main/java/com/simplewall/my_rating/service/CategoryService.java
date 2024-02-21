package com.simplewall.my_rating.service;

import com.simplewall.my_rating.model.entity.User;
import com.simplewall.my_rating.model.exception.RestException;
import com.simplewall.my_rating.model.request.category.AddCategoryRequest;
import com.simplewall.my_rating.model.request.category.DeleteCategoryRequest;
import com.simplewall.my_rating.model.request.category.UpdateCategoryRequest;
import com.simplewall.my_rating.repository.CategoryRepository;
import com.simplewall.my_rating.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseEntity<?> addCategory(String login, AddCategoryRequest request) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));

        Boolean isCategoryAdded = user.addCategory(request.getName(), request.getIcon());
        if (isCategoryAdded) {
            userRepository.save(user);
            return ResponseEntity.ok("Category added successfully");
        } else {
            return ResponseEntity.badRequest().body("Can't add category");
        }
    }

    @Transactional
    public ResponseEntity<?> updateCategory(String login, UpdateCategoryRequest request) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));

        Boolean isCategoryUpdated = user.updateCategory(request.getId(), request.getName(), request.getIcon());
        if (isCategoryUpdated) {
            userRepository.save(user);
            return ResponseEntity.ok("Category name updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Category not found for user");
        }
    }

    @Transactional
    public ResponseEntity<?> deleteCategory(String login, DeleteCategoryRequest request) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));

        boolean isCategoryDeleted = user.removeCategory(request.getCategoryId());
        if (isCategoryDeleted) {
            userRepository.save(user);
            categoryRepository.deleteById(request.getCategoryId());
            return ResponseEntity.ok("Category deleted from user and database successfully");
        } else {
            return ResponseEntity.badRequest().body("Category not found");
        }
    }
}
