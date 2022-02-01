package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.MedicineCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineCategoryRepository extends CrudRepository<MedicineCategory, Long> {

    @Query("Select mc from MedicineCategory mc where mc.medicine.id=:medicineId")
    List<MedicineCategory> findByMedicineId(Long medicineId);
}
