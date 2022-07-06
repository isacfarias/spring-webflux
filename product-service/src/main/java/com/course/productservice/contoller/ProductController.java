package com.course.productservice.contoller;


import com.course.productservice.dto.ProductDto;
import com.course.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("all")
    public Flux<ProductDto> all() {
        return this.service.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getProductId(@PathVariable String id) {
        return this.service.getByProductId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> insert(@RequestBody Mono<ProductDto> productDto) {
        return this.service.save(productDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> update(@PathVariable String id,
                                                          @RequestBody Mono<ProductDto> productDto) {
        return this.service.update(id, productDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return this.service.deleteProduct(id);
    }


}
