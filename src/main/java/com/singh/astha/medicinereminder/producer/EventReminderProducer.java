package com.singh.astha.medicinereminder.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.medicinereminder.dtos.kafka.NotificationRequest;
import com.singh.astha.medicinereminder.utils.Constants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventReminderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public EventReminderProducer(
            KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public boolean pushEvent(NotificationRequest notificationRequest) {
        try {
            kafkaTemplate.send(Constants.NOTIFICATION_INGESTION, objectMapper.writeValueAsString(notificationRequest));
        } catch (JsonProcessingException e) {
            return false;
        }
        return true;
    }
}
