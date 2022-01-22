package com.singh.astha.medicinereminder.exceptions;

import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException {

    private final int status;
    private final String message;
    private final transient Object payload;

    public ResponseException(int status, String message, Object payload) {
        super(message);
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

    public ResponseException(int status, String message) {
        this(status, message, null);
    }
}
