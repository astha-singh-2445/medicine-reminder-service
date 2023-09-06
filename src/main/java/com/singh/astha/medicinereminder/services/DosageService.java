package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.request.DosageHistoryRequestDto;
import com.singh.astha.medicinereminder.dtos.response.DosageHistoryResponseDto;
import com.singh.astha.medicinereminder.dtos.response.MedicineResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface DosageService {

    MedicineResponseDto updateDosage(Long userId, DosageHistoryRequestDto dosageHistoryRequestDto);

    List<DosageHistoryResponseDto> getDosage(Long userId, Integer page, Integer pageSize, Long medicineId);

    void deleteDosageHistory(Long dosageId, Long userId);

    DosageHistoryResponseDto updateDosageHistory(Long dosageId, Integer dosageCount, Long userId);
}
