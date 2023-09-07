package com.singh.astha.medicinereminder.dtos.transformers;

import com.singh.astha.medicinereminder.dtos.request.DosageHistoryRequestDto;
import com.singh.astha.medicinereminder.dtos.response.DosageHistoryResponseDto;
import com.singh.astha.medicinereminder.dtos.response.MedicineResponseDto;
import com.singh.astha.medicinereminder.enums.DosageType;
import com.singh.astha.medicinereminder.models.DosageHistory;
import com.singh.astha.medicinereminder.models.Medicine;
import org.springframework.stereotype.Component;

@Component
public class DosageDtoTransformer {

    public static DosageHistory setDosageHistory(Long userId, Medicine savedMedicine) {
        DosageHistory dosageHistory = new DosageHistory();
        dosageHistory.setMedicineId(savedMedicine.getId());
        dosageHistory.setMedicine(savedMedicine);
        dosageHistory.setDosage(savedMedicine.getCurrentDosage());
        dosageHistory.setType(DosageType.REFILL);
        dosageHistory.setUserId(userId);
        return dosageHistory;
    }

    public DosageHistory convertDosageHistoryRequestDtoToDosageHistory(DosageHistoryRequestDto dosageHistoryRequestDto,
                                                                       Long userId) {
        DosageHistory dosageHistory = new DosageHistory();
        dosageHistory.setDosage(dosageHistoryRequestDto.getDosage());
        dosageHistory.setType(DosageType.valueOf(dosageHistoryRequestDto.getType().trim().toUpperCase()));
        dosageHistory.setUserId(userId);
        return dosageHistory;
    }

    public DosageHistoryResponseDto convertDosageHistoryToDosageHistoryResponseDto(DosageHistory dosageHistory,
                                                                                   MedicineResponseDto medicine) {
        return DosageHistoryResponseDto.builder()
                .id(dosageHistory.getId())
                .dosage(dosageHistory.getDosage())
                .medicine(medicine)
                .type(dosageHistory.getType().name())
                .timeCreated(dosageHistory.getTimeCreated())
                .build();
    }
}
