package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.dtos.RequestDto.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.MedicineResponseDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.ResponseWrapper;
import com.singh.astha.medicinereminder.models.Medicine;
import com.singh.astha.medicinereminder.services.MedicineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/medicine")
public class MedicineController {


    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper<MedicineResponseDto>> addMedicine(Authentication authentication,
                                                                            @RequestBody @Valid MedicineRequestDto medicineRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        MedicineResponseDto medicineResponseDto = medicineService.addMedicine(medicineRequestDto, userId);
        return ResponseEntity.ok(ResponseWrapper.success(medicineResponseDto));
    }

    @PutMapping("/{medicineId}")
    public ResponseEntity<ResponseWrapper<MedicineResponseDto>> updateMedicine(Authentication authentication,
                                                                               @PathVariable Long medicineId,
                                                                               @RequestBody MedicineRequestDto medicineRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        MedicineResponseDto medicineResponseDto = medicineService.updateMedicine(medicineId, medicineRequestDto,
                userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(medicineResponseDto));
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<MedicineResponseDto>>> listAllMedicine(Authentication authentication,
                                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                                      @RequestParam(defaultValue = "50") Integer pageSize,
                                                                                      @RequestParam(required = false) Long categoryId) {
        Long userId = Long.valueOf(authentication.getName());
        List<MedicineResponseDto> medicineResponseDtos = medicineService.listAllMedicine(userId, page, pageSize,
                categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(medicineResponseDtos));
    }

    @PutMapping("/{medicineId}/category")
    public ResponseEntity<ResponseWrapper<Object>> updateCategoryOfMedicine(Authentication authentication,
                                                                            @PathVariable Long medicineId,
                                                                            @RequestParam Set<Long> categoriesId) {
        Long userId = Long.valueOf(authentication.getName());
        medicineService.updateCategoryOfMedicine(userId, medicineId, categoriesId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(null));
    }

    @GetMapping("/{medicineId}")
    public ResponseEntity<ResponseWrapper<MedicineResponseDto>> getMedicine(Authentication authentication,
                                                                            @PathVariable Long medicineId,
                                                                            @RequestParam(defaultValue = "false") boolean fetchCategories) {
        Long userId = Long.valueOf(authentication.getName());
        MedicineResponseDto medicineResponseDto = medicineService.getMedicine(userId, medicineId, fetchCategories);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(medicineResponseDto));

    }

    @DeleteMapping("/{medicineId}")
    public ResponseEntity<ResponseWrapper<Object>> deleteCategory(Authentication authentication,
                                                                  @PathVariable Long medicineId) {
        Long userId = Long.valueOf(authentication.getName());
        medicineService.deleteMedicine(medicineId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(null));
    }


    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<List<String>>> searchMedicine(Authentication authentication,
                                                                        @RequestParam String medicineName){
        Long userId = Long.valueOf(authentication.getName());
        List<String> medicine = medicineService.searchMedicine(userId, medicineName);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(medicine));
    }


}
