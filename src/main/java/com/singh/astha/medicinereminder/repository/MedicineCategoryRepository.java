package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.MedicineCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineCategoryRepository extends CrudRepository<MedicineCategory, Long> {
}
