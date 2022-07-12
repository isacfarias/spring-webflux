package com.course.orderservice.util;

import com.course.orderservice.dto.PurchaseOrderResponseDto;
import com.course.orderservice.dto.RequestContext;
import com.course.orderservice.dto.TransactionRequestDto;
import com.course.orderservice.entity.PurchaseOrder;
import com.course.orderservice.enums.OrderStatus;
import com.course.orderservice.enums.TransactionStatus;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static RequestContext setTransactionRequestDto(RequestContext requestContext) {

        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        dto.setAmount(requestContext.getProductDto().getPrice());

        requestContext.setTransactionRequestDto(dto);
        return requestContext;
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(requestContext.getProductDto().getPrice());

        TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        purchaseOrder.setStatus(orderStatus);

        return purchaseOrder;
    }

    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder, dto);
        dto.setOrderId(purchaseOrder.getId());
        return dto;
    }

}
