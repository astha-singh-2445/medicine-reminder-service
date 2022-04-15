package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.dtos.RequestDto.DosageHistoryRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.DosageHistoryResponseDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.MedicineResponseDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.ResponseWrapper;
import com.singh.astha.medicinereminder.services.DosageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/dosage")
public class DosageController {

    private final DosageService dosageService;

    public DosageController(DosageService dosageService) {
        this.dosageService = dosageService;
    }

    @PostMapping("/entry")
    public ResponseEntity<ResponseWrapper<MedicineResponseDto>> consumeOrRefillMedicine(Authentication authentication,
                                                                                        @RequestBody DosageHistoryRequestDto dosageHistoryRequestDto) throws ParseException {
        Long userId = Long.valueOf(authentication.getName());
        dosageService.updateDosage(userId, dosageHistoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(null));
    }

    @GetMapping("/history")
    public ResponseEntity<ResponseWrapper<List<DosageHistoryResponseDto>>> getDosage(Authentication authentication,
                                                                                     @RequestParam(defaultValue = "0") Integer page,
                                                                                     @RequestParam(defaultValue = "50") Integer pageSize,
                                                                                     @RequestParam(required = false) Long medicineId) {
        Long userId = Long.valueOf(authentication.getName());
        List<DosageHistoryResponseDto> dosage = dosageService.getDosage(userId, page, pageSize, medicineId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(dosage));
    }

    @DeleteMapping("/{dosageId}")
    public ResponseEntity<ResponseWrapper<Object>> deleteDosageHistory(Authentication authentication,
                                                                       @PathVariable Long dosageId) {
        Long userId = Long.valueOf(authentication.getName());
        dosageService.deleteDosageHistory(dosageId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(null));
    }

    @PatchMapping("/{dosageId}")
    public ResponseEntity<ResponseWrapper<DosageHistoryResponseDto>> updateDosageHistory(Authentication authentication,
                                                                                         @PathVariable Long dosageId,
                                                                                         @RequestParam Integer dosageCount) {
        Long userId = Long.valueOf(authentication.getName());
        DosageHistoryResponseDto dosageHistoryResponseDto = dosageService.updateDosageHistory(dosageId, dosageCount, userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.success(dosageHistoryResponseDto));
    }

}
