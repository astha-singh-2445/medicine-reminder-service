package com.singh.astha.medicinereminder.interceptor;

import com.singh.astha.medicinereminder.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = UUID.randomUUID().toString();
        request.setAttribute(Constants.REQUEST_ID, requestId);
        log.info("Pre Handle method is Calling {}", requestId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        String requestId = (String) request.getAttribute(Constants.REQUEST_ID);
        log.info("Post Handle method is Calling {}", requestId);
    }

    @Override
    public void afterCompletion
            (HttpServletRequest request, HttpServletResponse response, Object
                    handler, Exception exception) {
        String requestId = (String) request.getAttribute(Constants.REQUEST_ID);
        log.info("Request and Response is completed {}", requestId);
    }
}

