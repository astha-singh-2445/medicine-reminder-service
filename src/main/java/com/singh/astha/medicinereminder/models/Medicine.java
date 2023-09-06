package com.singh.astha.medicinereminder.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Medicine extends BaseModel {

    private String name;
    private Integer currentDosage;
    private Integer remindBeforeDosageCount;
    private String medicineType;
    private Long userId;

    @Column(columnDefinition = "json")
    private String meta;
}
