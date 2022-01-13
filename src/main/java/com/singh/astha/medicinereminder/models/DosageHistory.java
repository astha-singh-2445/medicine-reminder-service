package com.singh.astha.medicinereminder.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DosageHistory extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer dosage;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity= Medicine.class)
    @JoinColumn(name = "medicine_id")
    private Long medicineId;

    private Long userId;

}
