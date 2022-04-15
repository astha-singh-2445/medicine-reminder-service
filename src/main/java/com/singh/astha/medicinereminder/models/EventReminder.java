package com.singh.astha.medicinereminder.models;

import com.singh.astha.medicinereminder.enums.EventStatus;
import com.singh.astha.medicinereminder.enums.EventType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.Map;

@Document("event_reminder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EventReminder extends BaseDocument {

    private String reminderDate;
    private EventType eventType;
    private EventStatus status;

    @Column(columnDefinition = "json")
    private Map<String, Object> eventData;

    private Long userId;
}
