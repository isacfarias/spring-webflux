package com.course.webfluxdemo.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CalculatorHandler {

    public Mono<ServerResponse> additionHandler(ServerRequest request) {
        return process(request,
                (firstNumber, secondNumber) -> ServerResponse.ok()
                        .bodyValue(firstNumber.add(secondNumber)));
    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest request) {
        return process(request,
                (firstNumber, secondNumber) -> ServerResponse.ok()
                        .bodyValue(firstNumber.subtract(secondNumber)));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest request) {
        return process(request,
                (firstNumber, secondNumber) -> ServerResponse.ok()
                        .bodyValue(firstNumber.multiply(secondNumber)));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest request) {
        return process(request, (firstNumber, secondNumber) ->
                secondNumber.byteValue() != 0
                        ? ServerResponse.ok().bodyValue(firstNumber.divide(secondNumber, RoundingMode.HALF_EVEN))
                        : ServerResponse.badRequest().bodyValue("secondNumber can not be 0 !!")
        );
    }

    private Mono<ServerResponse> process(ServerRequest request,
                                         BiFunction<BigDecimal, BigDecimal, Mono<ServerResponse>> opLogic) {

        final var firstNumber = getValue(request, "firstNumber");
        final var secondNumber = getValue(request, "secondNumber");

        return opLogic.apply(firstNumber, secondNumber);
    }

    private BigDecimal getValue(ServerRequest request, String key) {
        return new BigDecimal(Integer.parseInt(request.pathVariable(key)), MathContext.DECIMAL32)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

}
