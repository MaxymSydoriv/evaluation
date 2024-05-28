package com.evaluation.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RestException extends RuntimeException {

    private HttpStatus httpStatus;

    public RestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public RestException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
