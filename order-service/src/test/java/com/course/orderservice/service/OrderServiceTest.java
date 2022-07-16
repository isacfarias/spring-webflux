package com.course.orderservice.service;


import com.course.orderservice.client.ProductClient;
import com.course.orderservice.client.UserClient;
import com.course.orderservice.dto.ProductDto;
import com.course.orderservice.dto.PurchaseOrderRequestDto;
import com.course.orderservice.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private UserClient userClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private OrderFulfillmentService fulfillmentService;

    @Test
    void purchaseOrderTest() {
        var purchaseOrder = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
                .map(tuple -> this.buildDto(tuple.getT1(), tuple.getT2()))
                .flatMap(dto -> this.fulfillmentService.processOrder(Mono.just(dto)))
                .doOnNext(msg -> log.info(msg.toString()));

        StepVerifier.create(purchaseOrder)
                .expectNextCount(4)
                .verifyComplete();
    }

    private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto) {
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
        dto.setProductId(productDto.getId());
        dto.setUserId(userDto.getId());
        return dto;
    }

}
