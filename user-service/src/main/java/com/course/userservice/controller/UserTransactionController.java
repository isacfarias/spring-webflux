package com.course.userservice.controller;

import com.course.userservice.dto.TransactionRequestDto;
import com.course.userservice.dto.TransactionResponseDto;
import com.course.userservice.entity.UserTransaction;
import com.course.userservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class UserTransactionController {

    private final TransactionService service;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> dto) {
        return dto
                .flatMap(this.service::createTransaction);
    }

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") int userId) {
        return this.service.getUserId(userId);
    }




}
