package com.course.webfluxdemo.webtestclient;

import com.course.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
@AutoConfigureWebTestClient
public class Lec01SimpleWebTestClientTest {

    @Autowired
    private WebTestClient client;

    @Test
    void webTestClientSimpleTest() {
        final var responses = this.client
                .get()
                .uri("/reactive-math/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(Response.class)
                .getResponseBody()
                .doOnNext(msg -> log.info(msg.toString()));

        StepVerifier.create(responses)
                .expectNextMatches(response -> response.getOutput() == 25 )
                .verifyComplete();
    }

    @Test
    void fluentAssertionsTest() {
        this.client
                .get()
                .uri("/reactive-math/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(result -> Assertions.assertThat(result.getOutput()).isEqualTo(25));
    }



}
