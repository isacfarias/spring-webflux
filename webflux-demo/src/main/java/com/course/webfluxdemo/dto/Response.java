package com.course.webfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

import java.time.Instant;

@Data
@ToString
public class Response {

    private Instant date = Instant.now();
    private int output;

    public Response(int output) {
        this.output = output;
    }

}
