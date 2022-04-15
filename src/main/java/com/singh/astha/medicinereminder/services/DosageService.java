package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.RequestDto.DosageHistoryRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.DosageHistoryResponseDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.MedicineResponseDto;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;

@Transactional
public interface DosageService {

    MedicineResponseDto updateDosage(Long userId, DosageHistoryRequestDto dosageHistoryRequestDto) throws ParseException;

    List<DosageHistoryResponseDto> getDosage(Long userId, Integer page, Integer pageSize, Long medicineId);

    void deleteDosageHistory(Long dosageId, Long userId);

    DosageHistoryResponseDto updateDosageHistory(Long dosageId, Integer dosageCount, Long userId);
}
