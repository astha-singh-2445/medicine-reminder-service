package com.singh.astha.medicinereminder.dtos.transformers;

import com.singh.astha.medicinereminder.dtos.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.MedicineResponseDto;
import com.singh.astha.medicinereminder.models.Medicine;
import org.springframework.stereotype.Component;

@Component
public class MedicineDtoTransformer {

    public Medicine convertMedicineRequestDtoToMedicine(MedicineRequestDto medicineRequestDto, Long userId) {
        Medicine medicine = new Medicine();
        medicine.setName(medicineRequestDto.getName());
        medicine.setCurrentDosage(medicineRequestDto.getCurrentDosage());
        medicine.setMedicineType(medicineRequestDto.getMedicineType());
        medicine.setUserId(userId);
        return medicine;
    }

    public MedicineResponseDto convertMedicineToMedicineResponseDto(Medicine medicine) {
        return MedicineResponseDto.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .currentDosage(medicine.getCurrentDosage())
                .medicineType(medicine.getMedicineType())
                .build();
    }

}
