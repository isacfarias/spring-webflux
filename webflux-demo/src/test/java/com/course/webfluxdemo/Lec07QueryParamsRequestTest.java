package com.course.webfluxdemo;

import com.course.webfluxdemo.dto.InputFailedValidationResponse;
import com.course.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

@Slf4j
class Lec07QueryParamsRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void queryParams() {
        final var url = "http://localhost:8085/jobs/search?count={count}&page={page}";
        final var uri = UriComponentsBuilder.fromUriString(url)
                .build(10, 20);

        final var response = this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(msg -> log.info(msg.toString()))
                .doOnError(error -> log.error(error.getMessage()));

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void queryParams2() {
        final var response = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("jobs/search")
                        .query("count={count}&page={page}")
                        .build(10, 20)
                )
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(msg -> log.info(msg.toString()))
                .doOnError(error -> log.error(error.getMessage()));

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void queryParams3() {

        final var map = Map.of(
                "count", 10,
                "page", 20
        );

        final var response = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("jobs/search")
                        .query("count={count}&page={page}")
                        .build(map)
                )
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(msg -> log.info(msg.toString()))
                .doOnError(error -> log.error(error.getMessage()));

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

}
