package com.course.productservice.service;

import com.course.productservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

    private final ProductService service;
    @Override
    public void run(String... args) throws Exception {
        final var produto1 = new ProductDto("LG-TV 4k", 8000);
        final var produto2 = new ProductDto("Cadeira escritorio", 300);
        final var produto3 = new ProductDto("Mesa jantar 4 cadeira", 400);
        final var produto4 = new ProductDto("Fogão", 800);
        final var produto5 = new ProductDto("Sofá 3 peças", 1500);
        final var produto6 = new ProductDto("Smartfone android", 3000);

        Flux.just(produto1, produto2, produto3, produto4, produto5, produto6)
                        .flatMap(p -> this.service.save(Mono.just(p)))
                        .subscribe(msg -> log.info(msg.toString()));
    }

    private List<ProductDto> products() {
        return  List.of(
                new ProductDto("LG-TV 4k", 8000),
                new ProductDto("Cadeira escritorio", 300),
                new ProductDto("Mesa jantar 4 cadeira", 400),
                new ProductDto("Fogão", 800),
                new ProductDto("Sofá 3 peças", 1500),
                new ProductDto("Smartfone android", 3000)
        );
    }
}
