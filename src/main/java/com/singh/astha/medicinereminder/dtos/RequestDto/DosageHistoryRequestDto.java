package com.singh.astha.medicinereminder.dtos.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
