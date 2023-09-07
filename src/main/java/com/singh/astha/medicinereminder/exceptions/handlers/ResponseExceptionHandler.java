package com.singh.astha.medicinereminder.exceptions.handlers;

import com.singh.astha.medicinereminder.dtos.response.ResponseWrapper;
import com.singh.astha.medicinereminder.exceptions.ResponseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseWrapper<Object>> handle(HttpServletRequest httpServletRequest,
                                                          ResponseException exception) {
        ResponseWrapper<Object> responseWrapper = ResponseWrapper.failure(exception.getPayload(),
                exception.getMessage());
        return new ResponseEntity<>(responseWrapper, exception.getStatus());
    }
}
