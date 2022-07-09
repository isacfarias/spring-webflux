package com.course.userservice.controller;

import com.course.userservice.dto.TransactionRequestDto;
import com.course.userservice.dto.TransactionResponseDto;
import com.course.userservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class UserTransactionController {

    private final TransactionService service;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> dto) {
        return dto
                .flatMap(this.service::createTrasaction);
    }



}
