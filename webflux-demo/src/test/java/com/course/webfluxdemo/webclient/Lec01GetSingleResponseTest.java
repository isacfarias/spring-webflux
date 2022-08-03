package com.course.webfluxdemo.webclient;

import com.course.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@Slf4j
class Lec01GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void blockTest() {
        final var response = this.webClient
                .get()
                .uri("reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(msg -> log.info(msg.toString()))
                .block();

        assert response != null;
        log.info(response.toString());
    }

    @Test
    void stepVerifierTest() {
       final var response = this.webClient
                .get()
                .uri("reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                ;

        StepVerifier.create(response)
                .expectNextMatches(payload -> payload.getOutput() == 25)
                .expectComplete();

    }

}
