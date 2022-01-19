package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.dtos.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.MedicineResponseDto;
import com.singh.astha.medicinereminder.services.MedicineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/medicine")
public class MedicineController {


    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping()
    public ResponseEntity<MedicineResponseDto> addMedicine(Authentication authentication,
                                                           @RequestBody @Valid MedicineRequestDto medicineRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(medicineService.addMedicine(medicineRequestDto, userId));
    }

    @PutMapping("/{medicineId}")
    public ResponseEntity<MedicineResponseDto> updateMedicine(Authentication authentication,
                                                              @PathVariable Long medicineId,
                                                              @RequestBody MedicineRequestDto medicineRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(medicineService.updateMedicine(medicineId, medicineRequestDto,
                userId));
    }

}