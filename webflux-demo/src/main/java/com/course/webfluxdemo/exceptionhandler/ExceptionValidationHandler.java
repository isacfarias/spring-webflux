package com.course.webfluxdemo.exceptionhandler;

import com.course.webfluxdemo.dto.InputFailedValidationResponse;
import com.course.webfluxdemo.exceptions.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handlerInputValidationException(InputValidationException ex) {
        return ResponseEntity
                .badRequest()
                .body(InputFailedValidationResponse
                        .builder()
                        .message(ex.getMessage())
                        .input(ex.getInput())
                        .errorCode(ex.getErrorCode())
                        .build());

    }
}
