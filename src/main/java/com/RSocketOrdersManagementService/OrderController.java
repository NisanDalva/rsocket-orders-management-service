package com.RSocketOrdersManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class OrderController {

	private OrderService orderService;

	
	@Autowired
	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}


	@MessageMapping("order-req-resp")
	public Mono<OrderBoundary> createOrUpdateOrder(OrderBoundary orderBoundary) {
		System.err.println("in controller, products = " + orderBoundary.getProducts());
		return this.orderService.createOrUpdateOrder(orderBoundary);
	}

	@MessageMapping("fulfill-fire-and-forget")
	public Mono<Void> fulfillOrder(OrderBoundary orderBoundary) {
		return this.orderService.fulfillOrder(orderBoundary);
	}

	@MessageMapping("getOpenOrderItems-stream")
	public Flux<OrderItemBoundary> getOpenOrderItems(UserBoundary user) {
		return this.orderService.getOpenOrderItems(user);
	}

	@MessageMapping("getOrders-stream")
	public Flux<OrderBoundary> getOrders(UserBoundary user) {
		return this.orderService.getOrders(user);
	}

	@MessageMapping("getItemsByOrder-channel")
	public Flux<OrderItemBoundary> getItemsByOrder(Flux<OrderBoundary> boundaries) {
		return this.orderService.getItemsByOrder(boundaries);
	}

	@MessageMapping("cleanup-fire-and-forget")
	public Mono<Void> clean() {
		return this.orderService
					.cleanup();
	}
}
