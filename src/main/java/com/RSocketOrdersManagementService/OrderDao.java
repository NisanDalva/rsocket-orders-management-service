package com.RSocketOrdersManagementService;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderDao extends ReactiveMongoRepository<OrderEntity, String> {
    
    Mono<OrderEntity> findByUserEmail(String userEmail);
    Flux<OrderEntity> findAllByUserEmailAndFulfilledTimestampIsNull(String userEmail);

    @Query(value="{'userEmail': ?0}", fields="{products : 0}") // find by email and ignore products field
    Flux<OrderEntity> findAllByUserEmailWithoutProducts(String userEmail);

    Flux<OrderEntity> findAllByOrderId(String orderId);

}
