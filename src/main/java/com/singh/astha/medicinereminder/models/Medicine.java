package com.singh.astha.medicinereminder.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

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
