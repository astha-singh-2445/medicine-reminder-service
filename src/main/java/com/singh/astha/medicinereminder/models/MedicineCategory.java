package com.singh.astha.medicinereminder.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicineCategory extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity= Medicine.class)
    @JoinColumn(name = "medicine_id")
    private Long medicineId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= Category.class)
    @JoinColumn(name = "category_id")
    private Long categoryId;

}
