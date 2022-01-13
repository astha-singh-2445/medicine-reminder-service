package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.CategoryRequest;

public interface CategoryService {

    Long addCategory(CategoryRequest categoryRequest, Long userName);

}
