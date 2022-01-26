package com.singh.astha.medicinereminder.dtos.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
