package com.course.webfluxdemo.config;

import com.course.webfluxdemo.dto.InputFailedValidationResponse;
import com.course.webfluxdemo.exceptions.InputValidationException;
import com.course.webfluxdemo.handlers.CalculatorHandler;
import com.course.webfluxdemo.handlers.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class CalculatorRouterConfig {

    private final CalculatorHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelCalculatorRouter() {
        return RouterFunctions.route()
                .path("calculator", this::serverResponseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("{firstNumber}/{secondNumber}", isOperation("+"), requestHandler::additionHandler)
                .GET("{firstNumber}/{secondNumber}", isOperation("-"), requestHandler::subtractionHandler)
                .GET("{firstNumber}/{secondNumber}", isOperation("*"), requestHandler::multiplicationHandler)
                .GET("{firstNumber}/{secondNumber}", isOperation("/"), requestHandler::divisionHandler)
                .GET("{firstNumber}/{secondNumber}", request -> ServerResponse.badRequest().bodyValue("Operation should be + - * /"))
                .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(headers -> operation.equals(headers.asHttpHeaders()
                .toSingleValueMap()
                .get("OPERATION"))
        );
    }



}
