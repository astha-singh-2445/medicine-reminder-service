package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.dtos.RequestDto.DosageHistoryRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.ResponseWrapper;
import com.singh.astha.medicinereminder.services.DosageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dosage")
public class DosageController {

    private final DosageService dosageService;

    public DosageController(DosageService dosageService) {
        this.dosageService = dosageService;
    }

    @PostMapping("/consume")
    public ResponseEntity<ResponseWrapper<Object>> consumeMedicine(Authentication authentication,
                                                                   @RequestBody DosageHistoryRequestDto dosageHistoryRequestDto){
        Long userId = Long.valueOf(authentication.getName());
        dosageService.consumeMedicine(userId, dosageHistoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(null));
    }

}
