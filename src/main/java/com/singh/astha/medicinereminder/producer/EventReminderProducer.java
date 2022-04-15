package com.singh.astha.medicinereminder.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.medicinereminder.dtos.kafka.NotificationRequest;
import com.singh.astha.medicinereminder.models.EventReminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventReminderProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();


    public String pushEvent(NotificationRequest notificationRequest) throws JsonProcessingException {
        kafkaTemplate.send("notification_ingestion", objectMapper.writeValueAsString(notificationRequest));
        return "Published successfully";
    }
}
