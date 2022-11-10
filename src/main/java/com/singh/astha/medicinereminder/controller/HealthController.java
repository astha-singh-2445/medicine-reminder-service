package com.singh.astha.medicinereminder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.singh.astha.medicinereminder.dtos.kafka.NotificationRequest;
import com.singh.astha.medicinereminder.enums.EventStatus;
import com.singh.astha.medicinereminder.enums.EventType;
import com.singh.astha.medicinereminder.models.EventReminder;
import com.singh.astha.medicinereminder.models.Medicine;
import com.singh.astha.medicinereminder.producer.EventReminderProducer;
import com.singh.astha.medicinereminder.repository.EventReminderRepository;
import com.singh.astha.medicinereminder.repository.MedicineRepository;
import com.singh.astha.medicinereminder.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/push")
    public void cronJobSch() throws JsonProcessingException {
        Date dateTime = new Date();
        String date = new SimpleDateFormat("yyyy/MM/dd").format(dateTime);
        List<EventReminder> eventReminderList = eventReminderRepository.findByReminderDateAndStatus(
                date, EventStatus.QUEUED);
        for (EventReminder eventReminder : eventReminderList) {
            if (eventReminder.getEventType().equals(EventType.REFILL_REMINDER)) {
                Object medicineId = eventReminder.getEventData().get(Constants.MEDICINE_ID);
                Optional<Medicine> medicineOptional = medicineRepository.findById((Long) medicineId);
                if (medicineOptional.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medicine Id not exist");
                }
                Medicine medicine = medicineOptional.get();
                if (medicine.getRemindBeforeDosageCount() == null) {
                    eventReminder.setStatus(EventStatus.DISCARDED);
                    continue;
                } else if (medicine.getCurrentDosage() <= medicine.getRemindBeforeDosageCount()) {
                    NotificationRequest notificationRequest = new NotificationRequest();
                    notificationRequest.setUserId(medicine.getUserId());
                    notificationRequest.setTemplateId("Medicine");
                    HashMap<String, String> title = new HashMap<>();
                    title.put("medicine-reminder", "Medicine");
                    notificationRequest.setTitlePlaceholder(title);
                    HashMap<String, String> values = new HashMap<>();
                    values.put("medicine-name", medicine.getName());
                    notificationRequest.setBodyPlaceHolders(values);
                    eventReminderProducer.pushEvent(notificationRequest);
                    eventReminder.setStatus(EventStatus.PROCESSED);

                } else {
                    eventReminder.setStatus(EventStatus.DISCARDED);
                }
                eventReminderRepository.save(eventReminder);
            }
        }
    }
}
