package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRespository extends CrudRepository<Category, Long> {

}
