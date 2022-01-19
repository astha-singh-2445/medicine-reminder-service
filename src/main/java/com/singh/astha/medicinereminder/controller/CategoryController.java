package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.dtos.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.CategoryResponseDto;
import com.singh.astha.medicinereminder.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDto> addCategory(Authentication authentication,
                                                           @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(categoryRequestDto, userId));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDto>> listAllCategory(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.listAllCategory(userId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Long> deleteCategory(Authentication authentication, @PathVariable Long categoryId) {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteCategory(categoryId, userId));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(Authentication authentication, @PathVariable Long categoryId,
                                                              @RequestBody CategoryRequestDto categoryRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(categoryId, categoryRequestDto,
                userId));
    }
}
