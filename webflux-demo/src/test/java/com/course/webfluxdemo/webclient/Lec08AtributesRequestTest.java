package com.course.webfluxdemo.webclient;

import com.course.webfluxdemo.dto.MultiplyRequestDto;
import com.course.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@Slf4j
class Lec08AtributesRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void postBasicAuth() {
        final var response = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(2, 5))
                .attribute("auth", "basic")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(msg -> log.info(msg.toString()));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void postOauth() {
        final var response = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(2, 5))
                .attribute("auth", "oauth")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(msg -> log.info(msg.toString()));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }

    private MultiplyRequestDto buildRequestDto(int firstNumber, int secondNumber) {
        MultiplyRequestDto dto = new MultiplyRequestDto();
        dto.setFirst(firstNumber);
        dto.setSecond(secondNumber);
        return dto;
    }


}
