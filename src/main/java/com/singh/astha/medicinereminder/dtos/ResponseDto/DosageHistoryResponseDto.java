package com.singh.astha.medicinereminder.dtos.ResponseDto;

import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DosageHistoryResponseDto {

    private Integer dosage;
    private String type;
    private MedicineResponseDto medicine;
    private OffsetDateTime timeCreated;
}
