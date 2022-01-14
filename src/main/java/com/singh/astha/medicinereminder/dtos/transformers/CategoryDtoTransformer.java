package com.singh.astha.medicinereminder.dtos.transformers;

import com.singh.astha.medicinereminder.dtos.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.CategoryResponseDto;
import com.singh.astha.medicinereminder.models.Category;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class CategoryDtoTransformer {

    public Category convertCategoryRequestDtoToCategory(CategoryRequestDto categoryRequestDto, Long userName) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        category.setUserId(userName);
        category.setDeleted(false);
        Date date = new Date();
        OffsetDateTime offsetDateTime = date.toInstant()
                .atOffset(ZoneOffset.UTC);
        category.setTimeCreated(offsetDateTime);
        category.setTimeLastModified(offsetDateTime);
        return category;
    }

    public CategoryResponseDto convertCategoryToCategoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
