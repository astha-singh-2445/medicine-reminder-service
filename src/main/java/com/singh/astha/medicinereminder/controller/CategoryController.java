package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.dtos.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.CategoryResponseDto;
import com.singh.astha.medicinereminder.dtos.ResponseWrapper;
import com.singh.astha.medicinereminder.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper<CategoryResponseDto>> addCategory(Authentication authentication,
                                                                            @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        CategoryResponseDto categoryResponseDto = categoryService.addCategory(categoryRequestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.success(categoryResponseDto));
    }

}
