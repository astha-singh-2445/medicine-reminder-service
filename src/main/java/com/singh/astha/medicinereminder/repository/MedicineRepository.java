package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.Medicine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Long> {

    Optional<Medicine> findByName(String name);

    Optional<Medicine> findByIdAndDeletedAndUserId(Long medicineId, boolean b, Long userId);
}
