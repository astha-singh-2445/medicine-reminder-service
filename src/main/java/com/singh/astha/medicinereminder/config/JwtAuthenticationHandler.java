package com.singh.astha.medicinereminder.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.medicinereminder.dtos.ResponseDto.ResponseWrapper;
import com.singh.astha.medicinereminder.exceptions.ResponseException;
import com.singh.astha.medicinereminder.services.JwtService;
import com.singh.astha.medicinereminder.utils.Constants;
import com.singh.astha.medicinereminder.utils.ErrorMessages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationHandler implements AuthenticationEntryPoint {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationHandler(JwtService jwtService) {
        this.jwtService = jwtService;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
        try {
            String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);
            if (authorizationHeader == null) {
                writeResponse(response, HttpStatus.BAD_REQUEST.value(),
                        ResponseWrapper.failure(null, ErrorMessages.AUTHORIZATION_HEADER_MUST_BE_PRESENT)
                );
                return;
            }
            jwtService.verifyAndDecodeToken(authorizationHeader);
        } catch (ResponseException responseException) {
            writeResponse(response, responseException.getStatus().value(),
                    ResponseWrapper.failure(responseException.getPayload(), responseException.getMessage())
            );
            return;
        }
        writeResponse(response, HttpStatus.FORBIDDEN.value(),
                ResponseWrapper.failure(null, ErrorMessages.ACCESS_DENIED));
    }

    private <T> void writeResponse(HttpServletResponse response, int httpStatus,
                                   ResponseWrapper<T> responseWrapper) throws IOException {
        response.setStatus(httpStatus);
        response.getWriter().println(objectMapper.writeValueAsString(responseWrapper));
    }
}
