package com.course.webfluxdemo.webtestclient;

import com.course.webfluxdemo.config.RouterConfig;
import com.course.webfluxdemo.dto.Response;
import com.course.webfluxdemo.handlers.RequestHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
class Lec05RouterFunctionTest {
    private WebTestClient client;

    @Autowired
    private RouterConfig config;

    @MockBean
    private RequestHandler handler;

    @BeforeAll
    void setClient() {
        this.client = WebTestClient.bindToRouterFunction(config.highLevelRouter())
                .build();
    }

    @Test
    void test() {
        Mockito.when(handler.squareHandler(Mockito.any()))
               .thenReturn(ServerResponse.ok().bodyValue(new Response(225)));

        this.client
                .get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(response -> Assertions.assertThat(response.getOutput()).isEqualTo(225))
                ;

    }
}
