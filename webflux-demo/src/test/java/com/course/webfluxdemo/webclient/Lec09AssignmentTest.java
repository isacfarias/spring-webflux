package com.course.webfluxdemo.webclient;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
class Lec09AssignmentTest extends BaseTest {


    private final static String FORMAT = "%d %s %d = %s";
    private final static int FIRST_NUMBER  = 10;

    @Autowired
    private WebClient webClient;

    @Test
    void process() {

       final var response = Flux.range(1, 5)
               .flatMap(secondNumber -> Flux.just("+", "-", "*", "/")
                       .flatMap(operation -> send(secondNumber, operation))
               ).doOnNext(msg -> log.info(msg.toString()));

        StepVerifier.create(response)
                .expectNextCount(20)
                .verifyComplete();
    }

    private Mono<String> send(int secondNumber, String operation){
        return this.webClient
                .get()
                .uri("calculator/{firstNumber}/{secondNumber}", FIRST_NUMBER, secondNumber)
                .headers(httpHeaders -> httpHeaders.set("OPERATION", operation))
                .retrieve()
                .bodyToMono(String.class)
                .map(value -> String.format(FORMAT, FIRST_NUMBER, operation, secondNumber, value))
                .doOnNext(msg -> log.info(msg.toString()));
    }

}
