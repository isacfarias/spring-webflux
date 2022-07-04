package com.course.webfluxdemo.dto;

import lombok.*;

@Data
@Builder
@With
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InputFailedValidationResponse {

    private int errorCode;
    private int input;
    private String message;
}
