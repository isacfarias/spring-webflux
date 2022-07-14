package com.course.orderservice.controller;


import com.course.orderservice.dto.PurchaseOrderRequestDto;
import com.course.orderservice.dto.PurchaseOrderResponseDto;
import com.course.orderservice.service.OrderFulfillmentService;
import com.course.orderservice.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class PurchaseOrderController {


    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService orderQueryService;


    @PostMapping
    public Mono<PurchaseOrderResponseDto> save(@RequestBody Mono<PurchaseOrderRequestDto> requestDto) {
        return this.orderFulfillmentService.processOrder(requestDto);
    }

    @GetMapping("user/{id}")
    public Flux<PurchaseOrderResponseDto> getOrderByUserId(@PathVariable("id") int userId) {
        return this.orderQueryService.getProductByUserId(userId);
    }


}
