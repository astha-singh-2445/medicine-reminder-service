package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Long> {

    Optional<Medicine> findByName(String name);

    Optional<Medicine> findByIdAndUserIdAndDeleted(Long medicineId, Long userId, boolean deleted);

    List<Medicine> findByUserIdAndDeleted(Long userId, Boolean deleted);

    @Query("SELECT m from Medicine m WHERE m.userId=:userId and (:categoryId is null or m.id in (SELECT mc.medicine" +
            ".id" +
            " from MedicineCategory mc WHERE mc.category.id=:categoryId))")
    Page<Medicine> findAll(Pageable pageable, Long userId, Long categoryId);
}
