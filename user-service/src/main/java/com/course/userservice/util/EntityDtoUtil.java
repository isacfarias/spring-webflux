package com.course.userservice.util;

import com.course.userservice.dto.TransactionRequestDto;
import com.course.userservice.dto.TransactionResponseDto;
import com.course.userservice.dto.UserDto;
import com.course.userservice.entity.User;
import com.course.userservice.entity.UserTransaction;
import com.course.userservice.enuns.TransactionStatus;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoUtil {

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static User toEntity(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user );
        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDto requestDto) {
        UserTransaction ut = new UserTransaction();
        ut.setUserId(requestDto.getUserId());
        ut.setAmount(requestDto.getAmount());
        ut.setTransactionDate(LocalDateTime.now());
        return ut;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto userTransaction, TransactionStatus status) {
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setUserId(userTransaction.getUserId());
        dto.setAmount(userTransaction.getAmount());
        dto.setStatus(status);
        return dto;
    }


}
