package com.course.orderservice.service;


import com.course.orderservice.client.ProductClient;
import com.course.orderservice.client.UserClient;
import com.course.orderservice.dto.PurchaseOrderRequestDto;
import com.course.orderservice.dto.PurchaseOrderResponseDto;
import com.course.orderservice.dto.RequestContext;
import com.course.orderservice.repository.PurchaseOrderRepository;
import com.course.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final ProductClient productClient;
    private final UserClient userClient;
    private final PurchaseOrderRepository repository;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDto) {
        return requestDto.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(this.repository::save)
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc) {
        return this.productClient
                .getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext) {
        return this.userClient
                .authorizeTransaction(requestContext.getTransactionRequestDto())
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }
}
