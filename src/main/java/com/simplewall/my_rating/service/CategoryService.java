package com.simplewall.my_rating.service;

import com.simplewall.my_rating.model.entity.Category;
import com.simplewall.my_rating.model.entity.User;
import com.simplewall.my_rating.model.exception.RestException;
import com.simplewall.my_rating.repository.CategoriesRepository;
import com.simplewall.my_rating.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    public ResponseEntity<?> addCategory(String login, Category category) {
        User user = usersRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));
        user.addCategory(category);
        usersRepository.save(user);
        return ResponseEntity.ok("Category added to user successfully");
    }

    public ResponseEntity<?> updateCategory(String login, Long categoryId, String newName) {
        User user = usersRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));
        Boolean updateCategory = user.updateCategory(categoryId, newName);
        if (updateCategory) {
            usersRepository.save(user);
            return ResponseEntity.ok("Category name updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Category not found for user");
        }
    }

    @Transactional
    public ResponseEntity<?> deleteCategory(String login, Long categoryId) {
        User user = usersRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        boolean categoryDeleted = user.removeCategory(categoryId);
        if (!categoryDeleted) {
            return ResponseEntity.badRequest().body("Category not found for user");
        }

        usersRepository.save(user);
        categoriesRepository.deleteById(categoryId);

        return ResponseEntity.ok("Category deleted from user and database successfully");
    }
}
