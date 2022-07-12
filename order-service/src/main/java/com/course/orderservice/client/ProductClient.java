package com.course.orderservice.client;

import com.course.orderservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${product.service.url}") String url) {
        this.webClient = WebClient
                .builder()
                .baseUrl(url)
                .build();
    }

    public Mono<ProductDto> getProductById(final String productId) {
        return this.webClient
                .get()
                .uri("{id}")
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

}
