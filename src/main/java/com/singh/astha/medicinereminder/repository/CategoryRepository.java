package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByUserIdAndName(Long userId, String name);

    Page<Category> findByUserIdAndDeleted(Pageable pageable, Long userId, Boolean deleted);

    Optional<Category> findByIdAndUserIdAndDeleted(Long id, Long userId, Boolean deleted);

    @Query("SELECT c from Category c WHERE c.userId=:userId and c.id IN :categoryId and c.deleted=:deleted")
    List<Category> findAll(Long userId, Set<Long> categoryId, boolean deleted);

    @Query("SELECT c from Category c where c.id in (select mc.category.id from MedicineCategory mc where mc.medicine" +
            ".id=:medicineId and mc.deleted=:deleted) and c.userId=:userId " +
            "and c.deleted=:deleted")
    List<Category> findByMedicineId(Long medicineId, Long userId, Boolean deleted);
}
