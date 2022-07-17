package com.course.webfluxdemo.webtestclient;

import com.course.webfluxdemo.controller.ReactiveMathController;
import com.course.webfluxdemo.dto.Response;
import com.course.webfluxdemo.services.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@WebFluxTest(ReactiveMathController.class)
public class Lec02ControllerGetTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService service;

    @Test
    void findSquareShould_Return_25_WhenSucessfull() {

        Mockito.when(service.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new Response(25)));

        this.client
                .get()
                .uri("/reactive-math/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(result -> Assertions.assertThat(result.getOutput()).isEqualTo(25));
    }


    @Test
    void findSquareShould_Return_negativeOne_WhenSucessfull() {

        Mockito.when(service.findSquare(Mockito.anyInt())).thenReturn(Mono.empty());

        this.client
                .get()
                .uri("/reactive-math/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(result -> Assertions.assertThat(result.getOutput()).isEqualTo(-1));
    }


    @Test
    void multiplicationTableShould_Return_List_WhenSucessfull() {

        final var payload = Flux.range(1,3)
        .map(Response::new);

        Mockito.when(service.multiplicationTable(Mockito.anyInt())).thenReturn(payload);

        this.client
                .get()
                .uri("/reactive-math/table/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Response.class)
                .hasSize(3);
    }

    @Test
    void multiplicationTableShould_Return_Exception_Whenfailed() {

        Mockito.when(service.multiplicationTable(Mockito.anyInt())).thenReturn(Flux.error(new IllegalArgumentException()));

        this.client
                .get()
                .uri("/reactive-math/table/{input}", 5)
                .exchange()
                .expectStatus().is5xxServerError();
    }


    @Test
    void multiplicationTableStreamingShould_Return_List_WhenSucessfull() {

        final var payload = Flux.range(1,3)
                .map(Response::new)
                .delayElements(Duration.ofMillis(100));

        Mockito.when(service.multiplicationTable(Mockito.anyInt())).thenReturn(payload);

        this.client
                .get()
                .uri("/reactive-math/table/{input}/stream", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(Response.class)
                .hasSize(3);
    }

}
