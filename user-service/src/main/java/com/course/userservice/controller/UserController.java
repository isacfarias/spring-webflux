package com.course.userservice.controller;

import com.course.userservice.dto.UserDto;
import com.course.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {


    private final UserService service;


    @GetMapping
    public Flux<UserDto> all() {
        return this.service.all();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable Integer id) {
        return this.service.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDto> save(@RequestBody Mono<UserDto> dto) {
        return this.service.save(dto);
    }


    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> update(@PathVariable Integer userId, @RequestBody Mono<UserDto> dto) {
        return this.service.update(userId, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable Integer userId) {
        return this.service.deleteUser(userId);
    }

}
