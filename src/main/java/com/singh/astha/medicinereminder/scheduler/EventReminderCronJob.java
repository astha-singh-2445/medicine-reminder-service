package com.singh.astha.medicinereminder.scheduler;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class EventReminderCronJob {

    private final EventReminderRepository eventReminderRepository;
    private final EventReminderProducer eventReminderProducer;
    private final MedicineRepository medicineRepository;

    public EventReminderCronJob(
            EventReminderRepository eventReminderRepository,
            EventReminderProducer eventReminderProducer,
            MedicineRepository medicineRepository) {
        this.eventReminderRepository = eventReminderRepository;
        this.eventReminderProducer = eventReminderProducer;
        this.medicineRepository = medicineRepository;
    }


    @Scheduled(cron = "0 0 22 * * ?")
    public void cronJobSch() {
        DateFormat dateFormat = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS);
        Date date = new Date();
        List<EventReminder> eventReminderList = eventReminderRepository.findByReminderDateAndStatus(
                dateFormat.format(date), EventStatus.QUEUED);
        for (EventReminder eventReminder : eventReminderList) {
            if (eventReminder.getEventType().equals(EventType.REFILL_REMINDER)) {
                Object medicineId = eventReminder.getEventData().get(Constants.MEDICINE_ID);
                Optional<Medicine> medicineOptional = medicineRepository.findById((Long) medicineId);
                if (medicineOptional.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.MEDICINE_ID_NOT_EXIST);
                }
                Medicine medicine = medicineOptional.get();
                if (medicine.getRemindBeforeDosageCount() == null) {
                    eventReminder.setStatus(EventStatus.DISCARDED);
                } else if (medicine.getCurrentDosage() <= medicine.getRemindBeforeDosageCount()) {
                    NotificationRequest notificationRequest = new NotificationRequest();
                    notificationRequest.setUserId(medicine.getUserId());
                    notificationRequest.setTemplateId("Medicine");
                    HashMap<String, String> title = new HashMap<>();
                    title.put("medicine-reminder", "Medicine");
                    notificationRequest.setTitlePlaceholder(title);
                    HashMap<String, String> values = new HashMap<>();
                    values.put(Constants.MEDICINE_NAME, medicine.getName());
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
