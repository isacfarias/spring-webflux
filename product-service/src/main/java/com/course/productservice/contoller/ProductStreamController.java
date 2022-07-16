package com.course.productservice.contoller;


import com.course.productservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductStreamController {

    private final Flux<ProductDto> productFlux;

    @GetMapping(value = "stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getProductUpdate(@PathVariable Integer maxPrice) {
        return this.productFlux.filter(productDto -> productDto.getPrice() <= maxPrice);
    }


}
