package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.MedicineResponseDto;

public interface MedicineService {

    MedicineResponseDto addMedicine(MedicineRequestDto medicineRequestDto, Long userId);

    MedicineResponseDto updateMedicine(Long medicineId, MedicineRequestDto medicineRequestDto, Long userId);
}
