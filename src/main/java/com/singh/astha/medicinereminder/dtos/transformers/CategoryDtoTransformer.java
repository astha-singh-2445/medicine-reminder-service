package com.singh.astha.medicinereminder.dtos.transformers;

import com.singh.astha.medicinereminder.dtos.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.CategoryResponseDto;
import com.singh.astha.medicinereminder.models.Category;
import com.singh.astha.medicinereminder.utils.Common;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoTransformer {

    public Category convertCategoryRequestDtoToCategory(CategoryRequestDto categoryRequestDto, Long userName) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        category.setUserId(userName);
        category.setDeleted(false);
        category.setTimeCreated(Common.getOffsetDateTime());
        category.setTimeLastModified(Common.getOffsetDateTime());
        return category;
    }

    public CategoryResponseDto convertCategoryToCategoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
