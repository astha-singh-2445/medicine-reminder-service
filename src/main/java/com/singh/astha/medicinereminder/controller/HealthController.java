package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping(value = "/health")
    public ResponseEntity<String> getHealthApi() {
        return ResponseEntity.ok(Constants.OK);
    }

}
