package com.course.webfluxdemo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.With;

@Data
@Builder
@With
@ToString
public class InputFailedValidationResponse {

    private int errorCode;
    private int input;
    private String message;
}
