package com.simplewall.my_rating.controller;

import com.simplewall.my_rating.model.entity.Category;
import com.simplewall.my_rating.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(
            @RequestParam String login,
            @RequestBody Category category
    ) {
        return categoryService.addCategory(login, category);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(
            @RequestParam String login,
            @RequestParam Long categoryId,
            @RequestParam String newName
    ) {
        return categoryService.updateCategory(login, categoryId, newName);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(
            @RequestParam String login,
            @RequestParam Long categoryId
    ) {
        return categoryService.deleteCategory(login, categoryId);
    }
}
