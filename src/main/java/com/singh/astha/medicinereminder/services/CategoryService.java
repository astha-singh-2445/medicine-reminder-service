package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.RequestDto.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.CategoryResponseDto;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CategoryService {

    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto, Long userName);

    List<CategoryResponseDto> listAllCategory(int page, int pageSize, Long userId);

    void deleteCategory(Long categoryId, Long userId);

    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryName, Long userId);

}
