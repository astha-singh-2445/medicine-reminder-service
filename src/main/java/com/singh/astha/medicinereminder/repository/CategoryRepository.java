package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);

    List<Category> findByUserIdAndDeleted(Long userId, Boolean deleted);

    Category findByIdAndDeletedAndUserId(Long id, Boolean deleted, Long userId);
}
