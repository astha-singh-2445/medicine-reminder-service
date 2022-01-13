package com.singh.astha.medicinereminder.models;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean deleted;
    private OffsetDateTime timeCreated;
    private OffsetDateTime timeLastModified;

}
