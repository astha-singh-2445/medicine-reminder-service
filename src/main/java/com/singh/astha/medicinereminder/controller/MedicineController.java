package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.dtos.RequestDto.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.MedicineResponseDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.ResponseWrapper;
import com.singh.astha.medicinereminder.services.MedicineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<ResponseWrapper<List<MedicineResponseDto>>> listAllMedicine(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        List<MedicineResponseDto> medicineResponseDtos = medicineService.listAllMedicine(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(medicineResponseDtos));
    }

}
