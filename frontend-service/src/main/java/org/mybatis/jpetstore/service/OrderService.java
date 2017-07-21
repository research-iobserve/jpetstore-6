/**
 *    Copyright (C) 2010-2017 the original author or authors.
 *                  2017 iObserve Project (https://www.iobserve-devops.net)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore.service;

import java.util.List;

import org.mybatis.jpetstore.domain.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class OrderService.
 *
 * @author Eduardo Macarron
 */
@Service
public class OrderService extends AbstractService {
	
	private static final String ORDER_SERVICE = "http://172.17.0.2:8080/jpetstore-order/";
	private static final String ORDERS_BY_USERNAME = ORDER_SERVICE + "orders-by-username?username=";
	private static final String ORDER_BY_ID = ORDER_SERVICE + "order-by-id?orderId=";
	private static final String INSERT_ORDER = ORDER_SERVICE + "insert-order";
	private static final String NEXT_ID = ORDER_SERVICE + "nextId?name=";

	/**
	 * Insert order.
	 *
	 * @param order
	 *            the order
	 */
	@Transactional
	public void insertOrder(final Order order) {
		postOperation(INSERT_ORDER, order);
	}

	/**
	 * Gets the order.
	 *
	 * @param orderId
	 *            the order id
	 * @return the order
	 */
	@Transactional
	public Order getOrder(final int orderId) {
		return getSingleValue(ORDER_BY_ID + orderId, Order.class);
	}

	/**
	 * Gets the orders by username.
	 *
	 * @param username
	 *            the username
	 * @return the orders by username
	 */
	public List<Order> getOrdersByUsername(final String username) {
		return getMultipleValues(ORDERS_BY_USERNAME + username, Order.class);
	}

	/**
	 * Gets the next id.
	 *
	 * @param name
	 *            the name
	 * @return the next id
	 */
	public int getNextId(final String name) {
		return getSingleValue(NEXT_ID, Integer.class);
	}

}
