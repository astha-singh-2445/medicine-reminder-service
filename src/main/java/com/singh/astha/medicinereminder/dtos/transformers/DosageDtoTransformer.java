package com.singh.astha.medicinereminder.dtos.transformers;

import com.singh.astha.medicinereminder.dtos.RequestDto.DosageHistoryRequestDto;
import com.singh.astha.medicinereminder.enums.DosageType;
import com.singh.astha.medicinereminder.models.DosageHistory;
import org.springframework.stereotype.Component;

@Component
public class DosageDtoTransformer {

    public DosageHistory convertDosageHistoryRequestDtoToDosageHistory(DosageHistoryRequestDto dosageHistoryRequestDto,
                                                                       Long userId) {
        DosageHistory dosageHistory = new DosageHistory();
        dosageHistory.setDosage(dosageHistoryRequestDto.getDosage());
        dosageHistory.setType(DosageType.valueOf(dosageHistoryRequestDto.getType().trim().toUpperCase()));
        dosageHistory.setUserId(userId);
        return dosageHistory;
    }
}
