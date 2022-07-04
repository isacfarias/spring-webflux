package com.course.webfluxdemo;

import com.course.webfluxdemo.dto.InputFailedValidationResponse;
import com.course.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
class Lec06ExchangeRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    // exchage = retrive + additonal info status code
    @Test
    void badRequest() {
        final var response = this.webClient
                .get()
                .uri("reactive-math/square/{input}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(msg -> log.info(msg.toString()))
                .doOnError(error -> log.error(error.getMessage()));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        if (clientResponse.rawStatusCode() == 400) {
            return clientResponse.bodyToMono(InputFailedValidationResponse.class);
        } else {
            return clientResponse.bodyToMono(Response.class);
        }
    }

}
