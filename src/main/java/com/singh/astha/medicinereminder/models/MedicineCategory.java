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
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicineCategory extends BaseModel {

    @ManyToOne(targetEntity = Medicine.class)
    @JoinColumn(name = "medicine_id")
    private Long medicineId;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Long categoryId;

}
