package com.singh.astha.medicinereminder.repository;

import com.singh.astha.medicinereminder.enums.EventStatus;
import com.singh.astha.medicinereminder.models.EventReminder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EventReminderRepository extends MongoRepository<EventReminder, String> {

    @Query("{'reminderDate':{'$lte':?0},'status':?1}")
    List<EventReminder> findByReminderDateAndStatus(String reminderDate, EventStatus status);
}
