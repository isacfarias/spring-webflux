package com.course.webfluxdemo.config;

import com.course.webfluxdemo.dto.InputFailedValidationResponse;
import com.course.webfluxdemo.exceptions.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", RequestPredicates.path("*/1?"), requestHandler::squareHandler)
                .GET("square/{input}", request -> ServerResponse.badRequest().bodyValue("only 10-19 allowed"))
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (exception, req) -> {
            InputValidationException ex = (InputValidationException) exception;
            final var response = InputFailedValidationResponse.builder()
                    .input(ex.getInput())
                    .message(ex.getMessage())
                    .errorCode(ex.getErrorCode())
                    .build();
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
