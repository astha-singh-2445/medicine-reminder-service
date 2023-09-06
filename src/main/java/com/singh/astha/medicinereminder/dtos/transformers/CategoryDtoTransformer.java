package com.singh.astha.medicinereminder.dtos.transformers;

import com.singh.astha.medicinereminder.dtos.request.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.response.CategoryResponseDto;
import com.singh.astha.medicinereminder.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoTransformer {

    public Category convertCategoryRequestDtoToCategory(CategoryRequestDto categoryRequestDto, Long userName) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        category.setUserId(userName);
        return category;
    }

    public CategoryResponseDto convertCategoryToCategoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
