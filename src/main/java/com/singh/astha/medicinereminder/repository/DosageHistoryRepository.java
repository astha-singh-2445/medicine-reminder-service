package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.Category;
import com.singh.astha.medicinereminder.models.DosageHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DosageHistoryRepository extends CrudRepository<DosageHistory, Long> {

}
