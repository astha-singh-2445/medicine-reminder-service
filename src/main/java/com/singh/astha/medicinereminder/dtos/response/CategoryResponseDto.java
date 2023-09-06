package com.singh.astha.medicinereminder.dtos.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {

    private Long id;
    private String name;

}
