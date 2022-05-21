package com.RSocketOrdersManagementService;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface OrderService {
    
    public Mono<OrderBoundary> createOrUpdateOrder(OrderBoundary orderBoundary);
    public Mono<Void> fulfillOrder(OrderBoundary orderBoundary);
    public Flux<OrderItemBoundary> getOpenOrderItems(UserBoundary user);
    public Flux<OrderBoundary> getOrders(UserBoundary user);
    Flux<OrderItemBoundary > getItemsByOrder(Flux<OrderBoundary> boundaries);
    public Mono<Void> cleanup();
}
