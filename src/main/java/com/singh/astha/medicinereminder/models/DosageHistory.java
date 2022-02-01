package com.singh.astha.medicinereminder.models;

import com.singh.astha.medicinereminder.enums.DosageType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class DosageHistory extends BaseModel {

    private Integer dosage;

    @Enumerated(value= EnumType.STRING)
    private DosageType type;

    @ManyToOne(targetEntity = Medicine.class)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private Long userId;

}
