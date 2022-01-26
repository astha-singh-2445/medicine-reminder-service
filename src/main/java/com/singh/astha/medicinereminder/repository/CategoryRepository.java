package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByUserIdAndName(Long userId, String name);

    Page<Category> findByUserIdAndDeleted(Pageable pageable, Long userId, Boolean deleted);

    Optional<Category> findByIdAndUserIdAndDeleted(Long id, Long userId, Boolean deleted);
}
