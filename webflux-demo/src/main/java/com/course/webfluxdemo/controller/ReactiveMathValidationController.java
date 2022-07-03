package com.course.webfluxdemo.controller;

import com.course.webfluxdemo.dto.MultiplyRequestDto;
import com.course.webfluxdemo.dto.Response;
import com.course.webfluxdemo.exceptions.InputValidationException;
import com.course.webfluxdemo.services.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathValidationController {

    private final ReactiveMathService service;

    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquere(@PathVariable int input) {
        if ( input < 10 || input > 20 ) throw new InputValidationException(input);
        return this.service.findSquare(input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if ( input >= 10 && input <= 20 )
                        sink.next(integer);
                    else
                        sink.error(new InputValidationException(input));

                })
                .cast(Integer.class)
                .flatMap( i -> this.service.findSquare(input));
    }

    @GetMapping("square/{input}/assigmnet")
    public Mono<ResponseEntity<Response>> assigmnet(@PathVariable int input) {
        return Mono.just(input)
                .filter( i -> i >= 10 && i <= 20)
                .flatMap( i -> this.service.findSquare(input))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
