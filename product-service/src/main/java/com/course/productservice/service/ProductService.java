package com.course.productservice.service;

import com.course.productservice.dto.ProductDto;
import com.course.productservice.repository.ProductRepository;
import com.course.productservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final Sinks.Many<ProductDto> sink;
    private final ProductRepository repository;

    public Flux<ProductDto> getAll() {
        return this.repository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getByProductId(String id) {
        return this.repository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> save(Mono<ProductDto> productDto) {
        return productDto
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.repository::save)
                .map(EntityDtoUtil::toDto)
                .doOnNext(this.sink::tryEmitNext);

    }

    public Mono<ProductDto> update(String id,  Mono<ProductDto> productDto) {
        return this.repository.findById(id)
                .flatMap(result -> productDto
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(entity -> entity.setId(id)))
                .flatMap(this.repository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return this.repository.deleteById(id);
    }


    public Flux<ProductDto> getPriceRange(int min, int max) {
        return this.repository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }
}
