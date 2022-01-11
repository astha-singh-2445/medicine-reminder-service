package com.singh.astha.medicinereminder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApi {

    @GetMapping(value = "/health")
    public ResponseEntity<String> getHealthApi(){
        return ResponseEntity.ok("OK");
    }
}
