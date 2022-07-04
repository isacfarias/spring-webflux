package com.course.webfluxdemo;

import com.course.webfluxdemo.dto.MultiplyRequestDto;
import com.course.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@Slf4j
class Lec04PostHeadersRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void post() {
        final var response = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(2, 5))
                .headers(header -> header.set("someKey", "someVal"))
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
