package com.singh.astha.medicinereminder.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRequestDto {

    @NotEmpty
    private String name;

    @NotNull
    @Min(0)
    private Integer currentDosage;
    private String medicineType;
    private Long categoryId;
}
