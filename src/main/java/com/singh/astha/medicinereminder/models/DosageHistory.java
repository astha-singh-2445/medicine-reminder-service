package com.singh.astha.medicinereminder.models;

import com.singh.astha.medicinereminder.enums.DosageType;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class DosageHistory extends BaseModel {

    private Integer dosage;

    @Enumerated(value = EnumType.STRING)
    private DosageType type;

    @ManyToOne(targetEntity = Medicine.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Column(name = "medicine_id", insertable = false, updatable = false)
    private Long medicineId;

    private Long userId;

}
