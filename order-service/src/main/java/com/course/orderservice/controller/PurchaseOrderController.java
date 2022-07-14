package com.course.orderservice.controller;


import com.course.orderservice.dto.PurchaseOrderRequestDto;
import com.course.orderservice.dto.PurchaseOrderResponseDto;
import com.course.orderservice.service.OrderFulfillmentService;
import com.course.orderservice.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class PurchaseOrderController {


    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService orderQueryService;


    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> save(@RequestBody Mono<PurchaseOrderRequestDto> requestDto) {
        return this.orderFulfillmentService.processOrder(requestDto)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("user/{id}")
    public Flux<PurchaseOrderResponseDto> getOrderByUserId(@PathVariable("id") int userId) {
        return this.orderQueryService.getProductByUserId(userId);
    }


}
