package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.dtos.RequestDto.CategoryRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.CategoryResponseDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.ResponseWrapper;
import com.singh.astha.medicinereminder.services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper<CategoryResponseDto>> addCategory(Authentication authentication,
                                                                            @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        CategoryResponseDto categoryResponseDto = categoryService.addCategory(categoryRequestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.success(categoryResponseDto));
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<CategoryResponseDto>>> listAllCategory(Authentication authentication,
                                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                                      @RequestParam(defaultValue =
                                                                                              "50") Integer pageSize
    ) {
        Long userId = Long.valueOf(authentication.getName());
        List<CategoryResponseDto> categoryResponseDtos = categoryService.listAllCategory(page, pageSize, userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(categoryResponseDtos));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseWrapper<Object>> deleteCategory(Authentication authentication,
                                                                  @PathVariable Long categoryId) {
        Long userId = Long.valueOf(authentication.getName());
        categoryService.deleteCategory(categoryId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(null));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseWrapper<CategoryResponseDto>> updateCategory(Authentication authentication,
                                                                               @PathVariable Long categoryId,
                                                                               @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId, categoryRequestDto,
                userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(categoryResponseDto));
    }
}
