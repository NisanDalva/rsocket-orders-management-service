package com.RSocketOrdersManagementService;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private ObjectMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.mapper = new ObjectMapper();
    }

    @Override
    public Mono<OrderBoundary> createOrUpdateOrder(OrderBoundary orderBoundary) {

        return this.orderDao
                .findByUserEmail(orderBoundary.getUserEmail())
                .map(entity -> updateEntity(entity, orderBoundary))
                .switchIfEmpty(createOrder(orderBoundary))
                .flatMap(this.orderDao::save)
                .map(this::entityToBoundary)
                .log();
    }

    private OrderEntity updateEntity(OrderEntity orderEntity, OrderBoundary orderBoundary) {

        List<Product> existingProducts = orderEntity.getProducts();

        orderBoundary.getProducts()
                .forEach(p -> {
                    int index = findIndexByProductId(existingProducts, p);

                    if (index >= 0) { // product exist, update the product itself
                        Product productToUpdate = existingProducts.get(index);

                        productToUpdate.setQuantity(productToUpdate.getQuantity() + p.getQuantity());

                        if (productToUpdate.getQuantity() <= 0)
                            existingProducts.remove(productToUpdate);
                        else
                            existingProducts.set(index, productToUpdate);

                    } else { // add a new product
                        if (p.getQuantity() > 0)
                            existingProducts.add(p);
                    }

                });

        orderEntity.setProducts(existingProducts);
        return orderEntity;
    }

    private Mono<OrderEntity> createOrder(OrderBoundary orderBoundary) {

        return Mono.just(boundaryToEntity(orderBoundary))
                .map(order -> {
                    order.setCreatedTimestamp(new Date());

                    order.getProducts()
                            .removeIf(p -> p.getQuantity() <= 0);

                    return order;
                });
    }

    private int findIndexByProductId(List<Product> products, Product product) {

        for (int i = 0; i < products.size(); i++)
            if (products.get(i).getProductId().equals(product.getProductId()))
                return i;

        return -1;
    }

    @Override
    public Mono<Void> fulfillOrder(OrderBoundary orderBoundary) {

        return this.orderDao
                .findByUserEmail(orderBoundary.getUserEmail())
                .map(order -> {
                    if (order.getFulfilledTimestamp() == null)
                        order.setFulfilledTimestamp(new Date());

                    return order;
                })
                .flatMap(this.orderDao::save)
                .map(this::entityToBoundary)
                .log()
                .then();
    }

    @Override
    public Flux<OrderItemBoundary> getOpenOrderItems(UserBoundary user) {

        return this.orderDao
                .findAllByUserEmailAndFulfilledTimestampIsNull(user.getUserEmail())
                .map(entity -> {

                    return entity.getProducts()
                            .stream()
                            .map(product -> {

                                OrderItemBoundary item = new OrderItemBoundary();

                                item.setOrderId(entity.getOrderId());
                                item.setProductId(product.getProductId());
                                item.setQuantity(product.getQuantity());

                                return item;

                            })
                            .toList();
                })
                .flatMap(iterable -> Flux.fromIterable(iterable))
                .log();
    }

    @Override
    public Flux<OrderBoundary> getOrders(UserBoundary user) {

        return this.orderDao
            .findAllByUserEmailWithoutProducts(user.getUserEmail())
            .map(this::entityToBoundary)
            .log();
    }

    @Override
    public Flux<OrderItemBoundary> getItemsByOrder(Flux<OrderBoundary> boundaries) {

        return boundaries
            .flatMap(order -> {

                return this.orderDao
                    .findAllByOrderId(order.getOrderId())
                    .map(entity -> {
                        return entity.getProducts()
                            .stream()
                            .map(product -> {

                                OrderItemBoundary item = new OrderItemBoundary();

                                item.setOrderId(entity.getOrderId());
                                item.setProductId(product.getProductId());
                                item.setQuantity(product.getQuantity());

                                return item;

                            })
                            .toList();
                    })
                    .flatMap(iterable -> Flux.fromIterable(iterable))
                    .log();
            })
            .log();
    }


    @Override
    public Mono<Void> cleanup() {
        return this.orderDao
                .deleteAll()
                .log();
    }

    private OrderEntity boundaryToEntity(OrderBoundary orderBoundary) {
        OrderEntity entity = null;

        try {
            String boundaryString = this.mapper.writeValueAsString(orderBoundary);
            entity = this.mapper.readValue(boundaryString, OrderEntity.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return entity;
    }

    private OrderBoundary entityToBoundary(OrderEntity orderEntity) {
        OrderBoundary boundary = null;

        try {
            String entityString = this.mapper.writeValueAsString(orderEntity);
            boundary = this.mapper.readValue(entityString, OrderBoundary.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return boundary;
    }

}
