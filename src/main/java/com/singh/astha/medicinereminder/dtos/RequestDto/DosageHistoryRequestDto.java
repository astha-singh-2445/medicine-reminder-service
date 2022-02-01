package com.singh.astha.medicinereminder.dtos.RequestDto;

import com.singh.astha.medicinereminder.models.Medicine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DosageHistoryRequestDto {
    private Integer dosage;
    private String type;
    private Long medicineId;
}
