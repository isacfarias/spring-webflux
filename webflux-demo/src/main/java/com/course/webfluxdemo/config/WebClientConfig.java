package com.course.webfluxdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8085")
                .filter(this::sessionToken)
                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction exchangeFunction) {
        log.info("generating session token");

        final var clientRequest = request.attribute("auth")
                .map(value -> value.equals("basic")
                        ? withBasicAuth(request)
                        : withOAuth(request)
                )
                .orElse(request);

        return exchangeFunction.exchange(clientRequest);
    }

    private ClientRequest withBasicAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBasicAuth("username", "password"))
                .build();
    }

    private ClientRequest withOAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBearerAuth("some-length-jwt"))
                .build();
    }


}
