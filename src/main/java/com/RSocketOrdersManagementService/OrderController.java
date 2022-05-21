package com.RSocketOrdersManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class OrderController {

	private OrderDao orderDao;
	private OrderService orderService;

	
	@Autowired
	public OrderController(OrderDao orderDao, OrderService orderService) {
		super();
		this.orderDao = orderDao;
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
		// return null;
	}


	@MessageMapping("cleanup-fire-and-forget")
	public Mono<Void> clean() {
		return this.orderService
					.cleanup();
	}


	// java -jar rsc-0.9.1.jar --debug --request --route req-resp-get-by-key tcp://localhost:7000
    @MessageMapping("req-resp-get-by-key")
	public Mono<OrderEntity> getValueByKey () {

		// OrderEntity entity = new OrderEntity();

		// entity.setCreatedTimestamp(new Date());
		// entity.setUserEmail("demo@email.com");

		// Product p1 = new Product();
		// p1.setProductId("p11");
		// p1.setQuantity(100);
		
		// Product p2 = new Product();
		// p2.setProductId("p22");
		// p2.setQuantity(200);

		// List<Product> products = new ArrayList();

		// products.add(p1);
		// products.add(p2);

		// entity.setProducts(products);
		// // entity.setFulfilledTimestamp(new Date());

		// return orderDao.save(entity).log();
		// return "saved2";

		return orderDao.findById("62753ab52d85b84833b0e395");

	}
    
}
