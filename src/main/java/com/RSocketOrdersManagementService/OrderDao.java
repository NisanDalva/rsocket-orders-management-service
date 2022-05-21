package com.RSocketOrdersManagementService;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderDao extends ReactiveMongoRepository<OrderEntity, String> {
    
    Mono<OrderEntity> findByUserEmail(String userEmail);
    Flux<OrderEntity> findAllByUserEmailAndFulfilledTimestampIsNull(String userEmail);

}
