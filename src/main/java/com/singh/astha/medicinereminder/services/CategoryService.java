package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto, Long userName);

    List<CategoryResponseDto> listAllCategory(Long userId);

    Long deleteCategory(Long categoryId, Long userId);

    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryName, Long aLong);

}