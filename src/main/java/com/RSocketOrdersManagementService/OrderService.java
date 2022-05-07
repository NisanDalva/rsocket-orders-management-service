package com.RSocketOrdersManagementService;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public interface OrderService {
    
    public Mono<OrderBoundary> createOrUpdateOrder(OrderBoundary orderBoundary);

    
    public Mono<Void> cleanup();


}
