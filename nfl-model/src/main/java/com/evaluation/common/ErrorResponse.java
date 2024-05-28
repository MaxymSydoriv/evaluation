package com.evaluation.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {

    private String message;
    private String path;
    private HttpStatus status;
}
