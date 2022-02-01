package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.models.DosageHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DosageHistoryRepository extends CrudRepository<DosageHistory, Long> {

    @Query("Select d from DosageHistory d where d.userId=:userId and (:medicineId is null or d.medicine" +
            ".id=:medicineId) and d.deleted=:deleted")
    Page<DosageHistory> findByUserIdAndMedicineIdAndDeleted(Pageable pageable, Long userId, Long medicineId,
                                                            boolean deleted);
}
