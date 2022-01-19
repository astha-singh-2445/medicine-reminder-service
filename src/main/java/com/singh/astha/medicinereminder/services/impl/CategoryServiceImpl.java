package com.singh.astha.medicinereminder.services.impl;

import com.singh.astha.medicinereminder.dtos.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.CategoryResponseDto;
import com.singh.astha.medicinereminder.dtos.transformers.CategoryDtoTransformer;
import com.singh.astha.medicinereminder.models.Category;
import com.singh.astha.medicinereminder.repository.CategoryRepository;
import com.singh.astha.medicinereminder.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoTransformer categoryDtoTransformer;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryDtoTransformer categoryDtoTransformer) {
        this.categoryRepository = categoryRepository;
        this.categoryDtoTransformer = categoryDtoTransformer;
    }

    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto, Long userName) {
        Category category = categoryDtoTransformer.convertCategoryRequestDtoToCategory(categoryRequestDto, userName);
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryRequestDto.getName());
        if (categoryOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same Category is already exist");
        }
        Category savedCategory = categoryRepository.save(category);
        return categoryDtoTransformer.convertCategoryToCategoryResponseDto(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> listAllCategory(Long userId) {
        List<Category> categoryOptional = categoryRepository.findByUserIdAndDeleted(userId, false);
        return categoryOptional.stream()
                .map(categoryDtoTransformer::convertCategoryToCategoryResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long deleteCategory(Long categoryId, Long userId) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndDeletedAndUserId(categoryId, false, userId);
        if (categoryOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not exist");
        }
        Category category = categoryOptional.get();
        category.setDeleted(true);
        Category deletedCategory = categoryRepository.save(category);
        return deletedCategory.getId();
    }

    @Override
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryName, Long userId) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndDeletedAndUserId(categoryId, false, userId);
        if (categoryOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not exist");
        }
        Category category = categoryDtoTransformer.convertCategoryRequestDtoToCategory(categoryName, userId);
        Category updatedCategory = categoryRepository.save(category);
        return categoryDtoTransformer.convertCategoryToCategoryResponseDto(updatedCategory);
    }
}
