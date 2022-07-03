package com.course.webfluxdemo.controller;

import com.course.webfluxdemo.dto.MultiplyRequestDto;
import com.course.webfluxdemo.dto.Response;
import com.course.webfluxdemo.services.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {

    private final ReactiveMathService service;

    @GetMapping("square/{input}")
    public Mono<Response> findSquere(@PathVariable int input) {
        return this.service.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return this.service.multiplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return this.service.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> dto) {
        return this.service.multiply(dto);
    }

}
