package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.producer.EventReminderProducer;
import com.singh.astha.medicinereminder.repository.EventReminderRepository;
import com.singh.astha.medicinereminder.repository.MedicineRepository;
import com.singh.astha.medicinereminder.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final EventReminderRepository eventReminderRepository;
    private final EventReminderProducer eventReminderProducer;
    private final MedicineRepository medicineRepository;

    public HealthController(EventReminderRepository eventReminderRepository,
                            EventReminderProducer eventReminderProducer,
                            MedicineRepository medicineRepository) {
        this.eventReminderRepository = eventReminderRepository;
        this.eventReminderProducer = eventReminderProducer;
        this.medicineRepository = medicineRepository;
    }


    @GetMapping(value = "/health")
    public ResponseEntity<String> getHealthApi() {
        return ResponseEntity.ok(Constants.OK);
    }

}
