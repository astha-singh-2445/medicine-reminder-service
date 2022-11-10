package com.singh.astha.medicinereminder.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class BaseDocument {

    protected Long timeCreated;
    protected Long timeLastModified;
    @Id
    private String id;
}
