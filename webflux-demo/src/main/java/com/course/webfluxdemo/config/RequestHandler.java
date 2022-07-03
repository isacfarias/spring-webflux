package com.course.webfluxdemo.config;


import com.course.webfluxdemo.dto.MultiplyRequestDto;
import com.course.webfluxdemo.dto.Response;
import com.course.webfluxdemo.services.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {

    private final ReactiveMathService service;


    public Mono<ServerResponse> squareHandler(ServerRequest request) {
        final var input = Integer.parseInt(request.pathVariable("input"));
        final var response = this.service.findSquare(input);
        return ServerResponse.ok().body(response, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        final var input = Integer.parseInt(request.pathVariable("input"));
        final var response = this.service.multiplicationTable(input);
        return ServerResponse.ok().body(response, Response.class);
    }


    public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
        final var input = Integer.parseInt(request.pathVariable("input"));
        final var response = this.service.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(response, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
        final var requestDtoMono = request.bodyToMono(MultiplyRequestDto.class);
        final var response = this.service.multiply(requestDtoMono);
        return ServerResponse.ok()
                .body(response, Response.class);
    }

}
