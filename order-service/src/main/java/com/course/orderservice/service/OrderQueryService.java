package com.course.orderservice.service;

import com.course.orderservice.dto.PurchaseOrderResponseDto;
import com.course.orderservice.repository.PurchaseOrderRepository;
import com.course.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final PurchaseOrderRepository repository;

    public Flux<PurchaseOrderResponseDto> getProductByUserId(int userId) {
      return Flux.fromStream(() -> this.repository.findByUserId(userId).stream())
              .map(EntityDtoUtil::getPurchaseOrderResponseDto)
              .subscribeOn(Schedulers.boundedElastic());
    }

}
