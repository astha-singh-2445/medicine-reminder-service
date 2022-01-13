package com.singh.astha.medicinereminder.dtos.transformers;

import com.singh.astha.medicinereminder.dtos.CategoryRequest;
import com.singh.astha.medicinereminder.models.Category;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class CategoryDtoTransformer {

    public static Category convertCategoryRequestDtoToCategory(CategoryRequest categoryRequest, Long userName) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setUserId(userName);
        category.setDeleted(false);
        Date date = new Date();
        OffsetDateTime offsetDateTime = date.toInstant()
                .atOffset(ZoneOffset.UTC);
        category.setTimeCreated(offsetDateTime);
        category.setTimeLastModified(offsetDateTime);
        return category;

    }


}
