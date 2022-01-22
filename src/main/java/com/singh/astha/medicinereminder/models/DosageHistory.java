package com.singh.astha.medicinereminder.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DosageHistory extends BaseModel {

    private Integer dosage;
    private String type;

    @ManyToOne(targetEntity = Medicine.class)
    @JoinColumn(name = "medicine_id")
    private Long medicineId;

    private Long userId;

}
