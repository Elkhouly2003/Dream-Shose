package com.example.dream_shops.controller;

import com.example.dream_shops.exceptions.AlreadyExistsException;
import com.example.dream_shops.model.Category;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found !",categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to get categories",e.getMessage()));
        }
    }

    @PostMapping("/add/{category}")
    public ResponseEntity<ApiResponse> AddCategory(@RequestBody Category category) {

        try {
            Category newCategory =   categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Added Success !",newCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Category already exists !",null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {

        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found !",category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found !",null));
        }

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {

        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found !",category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found !",null));
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {

        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted Success !",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found !",null));
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category newCategory) {

        try {
            Category category = categoryService.updateCategory(newCategory,id);
            return ResponseEntity.ok(new ApiResponse("Updated Success !",category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found !",null));
        }

    }



}
