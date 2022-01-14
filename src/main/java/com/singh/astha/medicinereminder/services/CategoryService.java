package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.CategoryResponseDto;

public interface CategoryService {

    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto, Long userName);

}
