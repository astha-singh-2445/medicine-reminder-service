package com.singh.astha.medicinereminder.services;

import com.singh.astha.medicinereminder.dtos.RequestDto.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.MedicineResponseDto;
import com.singh.astha.medicinereminder.models.Medicine;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Transactional
public interface MedicineService {

    MedicineResponseDto addMedicine(MedicineRequestDto medicineRequestDto, Long userId);

    MedicineResponseDto updateMedicine(Long medicineId, MedicineRequestDto medicineRequestDto, Long userId);

    List<MedicineResponseDto> listAllMedicine(Long userId, Integer page, Integer pageSize, Long categoryId);

    void updateCategoryOfMedicine(Long userId, Long medicineId, Set<Long> categoriesId);

    MedicineResponseDto getMedicine(Long userId, Long medicineId, boolean fetchCategories);

    void deleteMedicine(Long medicineId, Long userId);

    List<String> searchMedicine(Long userId, String medicineName);
}
