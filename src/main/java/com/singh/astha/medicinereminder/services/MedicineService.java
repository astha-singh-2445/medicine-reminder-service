package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.RequestDto.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.MedicineResponseDto;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MedicineService {

    MedicineResponseDto addMedicine(MedicineRequestDto medicineRequestDto, Long userId);

    MedicineResponseDto updateMedicine(Long medicineId, MedicineRequestDto medicineRequestDto, Long userId);

    List<MedicineResponseDto> listAllMedicine(Long userId);
}
