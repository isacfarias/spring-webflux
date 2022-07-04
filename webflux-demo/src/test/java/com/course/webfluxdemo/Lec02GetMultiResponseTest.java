package com.course.webfluxdemo;

import com.course.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@Slf4j
class Lec02GetMultiResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void stepVerifierTest() {
        final var response = this.webClient
                .get()
                .uri("reactive-math/table/{input}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println)
                ;

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();

    }

}
