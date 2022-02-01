package com.singh.astha.medicinereminder.services.impl;

import com.singh.astha.medicinereminder.dtos.RequestDto.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.CategoryResponseDto;
import com.singh.astha.medicinereminder.dtos.transformers.CategoryDtoTransformer;
import com.singh.astha.medicinereminder.exceptions.ResponseException;
import com.singh.astha.medicinereminder.models.Category;
import com.singh.astha.medicinereminder.repository.CategoryRepository;
import com.singh.astha.medicinereminder.services.CategoryService;
import com.singh.astha.medicinereminder.utils.Constants;
import com.singh.astha.medicinereminder.utils.ErrorMessages;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        Optional<Category> categoryOptional = categoryRepository.findByUserIdAndName(userName,
                categoryRequestDto.getName());
        if (categoryOptional.isPresent()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.SAME_CATEGORY_IS_ALREADY_EXIST);
        }
        Category savedCategory = categoryRepository.save(category);
        return categoryDtoTransformer.convertCategoryToCategoryResponseDto(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> listAllCategory(int page, int pageSize, Long userId) {
        Page<Category> categoryPage = categoryRepository.findByUserIdAndDeleted(PageRequest.of(page, pageSize,
                Sort.by("id")), userId, false
        );
        return categoryPage.stream()
                .map(categoryDtoTransformer::convertCategoryToCategoryResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long categoryId, Long userId) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndUserIdAndDeleted(categoryId, userId, false);
        if (categoryOptional.isEmpty()) {
            return;
        }
        Category category = categoryOptional.get();
        JSONObject meta = category.getMeta() == null ? new JSONObject() : new JSONObject(category.getMeta());
        meta.put(Constants.NAME, category.getName());
        category.setName(UUID.randomUUID().toString());
        category.setMeta(meta.toString());
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto, Long userId) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndUserIdAndDeleted(categoryId, userId, false);
        if (categoryOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.CATEGORY_NOT_EXIST);
        }
        Optional<Category> categoryOptionalByName = categoryRepository.findByUserIdAndName(userId,
                categoryRequestDto.getName());
        if (categoryOptionalByName.isPresent()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.SAME_CATEGORY_IS_ALREADY_EXIST);
        }
        Category category = categoryOptional.get();
        category.setName(categoryRequestDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return categoryDtoTransformer.convertCategoryToCategoryResponseDto(updatedCategory);
    }
}
