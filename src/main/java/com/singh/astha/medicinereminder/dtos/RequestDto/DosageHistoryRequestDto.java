package com.singh.astha.medicinereminder.dtos.RequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DosageHistoryRequestDto {

    @NotNull
    private Integer dosage;
    @NotBlank
    private String type;
    @NotNull
    private Long medicineId;
}
