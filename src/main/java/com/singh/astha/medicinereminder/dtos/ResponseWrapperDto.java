package com.singh.astha.medicinereminder.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseWrapperDto<T> {

    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public static <T> ResponseWrapperDto<T> success(T payload, String message) {
        return new ResponseWrapperDto<>(true, payload, message);
    }

    public static <T> ResponseWrapperDto<T> success(T payload) {
        return success(payload, null);
    }

    public static <T> ResponseWrapperDto<T> failure(T payload, String message) {
        return new ResponseWrapperDto<>(false, payload, message);
    }

    public static <T> ResponseWrapperDto<T> failure(T payload) {
        return failure(payload, null);
    }
}
