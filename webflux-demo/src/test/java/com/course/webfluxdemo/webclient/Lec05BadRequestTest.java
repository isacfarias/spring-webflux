package com.course.webfluxdemo.webclient;

import com.course.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

@Slf4j
class Lec05BadRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void badRequest() {
        final var response = this.webClient
                .get()
                .uri("reactive-math/square/{input}/throw", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(msg -> log.info(msg.toString()))
                .doOnError(error -> log.error(error.getMessage()));

        StepVerifier.create(response)
                    .verifyError(WebClientResponseException.BadRequest.class);
    }

}
