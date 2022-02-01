package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.RequestDto.DosageHistoryRequestDto;

import javax.transaction.Transactional;

@Transactional
public interface DosageService {
    void consumeMedicine(Long userId, DosageHistoryRequestDto dosageHistoryRequestDto);
}
