package com.singh.astha.medicinereminder.dtos.request;

import jakarta.validation.constraints.Min;
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
public class MedicineRequestDto {

    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private Integer currentDosage;
    private String medicineType;
}
