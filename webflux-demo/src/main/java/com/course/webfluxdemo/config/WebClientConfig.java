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

        final var clientRequest = ClientRequest.from(request)
                .headers(header -> header.setBearerAuth("some-length-jwt"))
                .build();

        return exchangeFunction.exchange(clientRequest);
    }
}
