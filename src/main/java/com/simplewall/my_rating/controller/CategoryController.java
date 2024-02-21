package com.simplewall.my_rating.controller;

import com.simplewall.my_rating.model.request.category.AddCategoryRequest;
import com.simplewall.my_rating.model.request.category.DeleteCategoryRequest;
import com.simplewall.my_rating.model.request.category.UpdateCategoryRequest;
import com.simplewall.my_rating.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(
            @RequestParam String login,
            @RequestBody AddCategoryRequest addCategoryRequest
    ) {
        return categoryService.addCategory(login, addCategoryRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(
            @RequestParam String login,
            @RequestBody UpdateCategoryRequest updateCategoryRequest
    ) {
        return categoryService.updateCategory(login, updateCategoryRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(
            @RequestParam String login,
            @RequestBody DeleteCategoryRequest deleteCategoryRequest
    ) {
        return categoryService.deleteCategory(login, deleteCategoryRequest);
    }
}
