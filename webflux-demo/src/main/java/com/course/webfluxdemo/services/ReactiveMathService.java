package com.course.webfluxdemo.services;

import com.course.webfluxdemo.dto.MultiplyRequestDto;
import com.course.webfluxdemo.dto.Response;
import com.course.webfluxdemo.util.SleepUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.IntStream;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                //.doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("reactive-math-service processing:".concat(i+"")))
                .map(i -> new Response(i * input));

    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> requestMono) {
        return requestMono
                .map(d -> d.getFirst() * d.getSecond())
                .map(Response::new);
    }
}
