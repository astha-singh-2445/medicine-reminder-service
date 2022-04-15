package com.singh.astha.medicinereminder.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class BaseDocument {
    @Id
    private String id;
    private Long timeCreated;
    private Long timeLastModified;
}
