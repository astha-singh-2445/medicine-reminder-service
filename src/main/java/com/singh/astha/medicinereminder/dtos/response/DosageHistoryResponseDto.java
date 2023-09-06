package com.singh.astha.medicinereminder.dtos.response;

import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DosageHistoryResponseDto {

    private Long id;
    private Integer dosage;
    private String type;
    private MedicineResponseDto medicine;
    private OffsetDateTime timeCreated;
}
