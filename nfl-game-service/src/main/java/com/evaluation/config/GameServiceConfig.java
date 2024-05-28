package com.evaluation.config;

import com.evaluation.common.ErrorResponse;
import com.evaluation.exception.RestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
@ControllerAdvice
public class GameServiceConfig {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, RestException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(exception.getHttpStatus());
        response.setPath(response.getPath());
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
}
