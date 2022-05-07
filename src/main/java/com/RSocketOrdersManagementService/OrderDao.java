package com.RSocketOrdersManagementService;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface OrderDao extends ReactiveMongoRepository<OrderEntity, String> {
    
    Mono<OrderEntity> findByUserEmail(String userEmail);
}
