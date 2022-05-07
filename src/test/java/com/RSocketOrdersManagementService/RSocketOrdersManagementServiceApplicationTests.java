package com.RSocketOrdersManagementService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Mono;

@SpringBootTest
class RSocketOrdersManagementServiceApplicationTests {

	@Autowired
	private OrderDao orderDao;


	@Test
	void contextLoads() {
	}

	@Test
	void saveList() {

		OrderEntity entity = new OrderEntity();

		entity.setCreatedTimestamp(new Date());
		entity.setUserEmail("demo@email.com");

		Product p1 = new Product();
		p1.setProductId("p111");
		p1.setQuantity(1000);
		
		Product p2 = new Product();
		p2.setProductId("p222");
		p2.setQuantity(2000);

		Product p3 = new Product();
		p3.setProductId("p333");
		// p3.setQuantity(3000);

		List<Product> products = new ArrayList();

		products.add(p1);
		products.add(p2);
		products.add(p3);

		entity.setProducts(products);
		entity.setFulfilledTimestamp(new Date());

		orderDao.save(entity).block();

		// orderDao.deleteAll().block();
	}

}
