package com.course.orderservice.dto;

import com.course.orderservice.enums.TransactionStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionResponseDto {

    private Integer userId;
    private Integer amount;
    private TransactionStatus status;

}
