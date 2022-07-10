package com.course.orderservice.dto;

import com.course.orderservice.enums.OrderStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderResponseDto {

    private Integer orderId;
    private Integer userId;
    private String productId;
    private Integer amount;
    private OrderStatus status;
}
