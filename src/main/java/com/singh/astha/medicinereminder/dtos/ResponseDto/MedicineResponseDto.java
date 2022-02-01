package com.singh.astha.medicinereminder.dtos.ResponseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.singh.astha.medicinereminder.models.Category;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineResponseDto {

    private Long id;
    private String name;
    private Integer currentDosage;
    private String medicineType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CategoryResponseDto> categories;
}
