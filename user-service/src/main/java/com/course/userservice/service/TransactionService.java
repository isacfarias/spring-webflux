package com.course.userservice.service;

import com.course.userservice.dto.TransactionRequestDto;
import com.course.userservice.dto.TransactionResponseDto;
import com.course.userservice.entity.UserTransaction;
import com.course.userservice.enuns.TransactionStatus;
import com.course.userservice.repository.UserRepository;
import com.course.userservice.repository.UserTransactionRepository;
import com.course.userservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository transactionRepository;

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
        return this.userRepository
                .updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(isTrue -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(this.transactionRepository::save)
                .map(userTransaction -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getUserId(int userId) {
        return this.transactionRepository.findByUserId(userId);
    }




}
