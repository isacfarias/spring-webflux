package com.course.webfluxdemo;

import com.course.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
class Lec01GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void blockTest() {
        var response = this.webClient
                .get()
                .uri("reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        assert response != null;
        log.info(response.toString());
    }


}
