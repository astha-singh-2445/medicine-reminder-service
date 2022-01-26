package com.singh.astha.medicinereminder.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseException extends RuntimeException {

    private final HttpStatus status;
    private final String message;
    private final transient Object payload;

    public ResponseException(HttpStatus status, String message, Object payload) {
        super(message);
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

    public ResponseException(HttpStatus status, String message) {
        this(status, message, null);
    }
}
