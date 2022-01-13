package com.singh.astha.medicinereminder.services.impl;

import com.singh.astha.medicinereminder.dtos.CategoryRequest;
import com.singh.astha.medicinereminder.dtos.transformers.CategoryDtoTransformer;
import com.singh.astha.medicinereminder.models.Category;
import com.singh.astha.medicinereminder.repository.CategoryRespository;
import com.singh.astha.medicinereminder.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRespository categoryRespository;

    @Override
    public Long addCategory(CategoryRequest categoryRequest, Long userName) {
        if (categoryRequest.getName()!=null) {
            Category category = CategoryDtoTransformer.convertCategoryRequestDtoToCategory(categoryRequest,userName);

            Category savedCategory = null;
            try {
                savedCategory = categoryRespository.save(category);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Same Category is already exist");
            }
            return savedCategory.getId();
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Category name is missing");
        }
    }
}
