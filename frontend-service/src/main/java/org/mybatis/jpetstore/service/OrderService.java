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

/**
 * The Class OrderService.
 *
 * @author Eduardo Macarron
 * @author Reiner Jung -- adapted for distributed JPetStore
 */
@Service
public class OrderService extends AbstractService {

    private static final String ORDER_SERVICE = "http://order" + AbstractService.getDomain() + ":8080/jpetstore-order/";
    private static final String ORDERS_BY_USERNAME = OrderService.ORDER_SERVICE + "orders-by-username?username=";
    private static final String ORDER_BY_ID = OrderService.ORDER_SERVICE + "order-by-id?orderId=";
    private static final String INSERT_ORDER = OrderService.ORDER_SERVICE + "insert-order";
    private static final String NEXT_ID = OrderService.ORDER_SERVICE + "nextId?name=";

    /**
     * Insert order.
     *
     * @param order
     *            the order
     */
    public void insertOrder(final Order order) {
        this.postOperation(OrderService.INSERT_ORDER, order);
    }

    /**
     * Gets the order.
     *
     * @param orderId
     *            the order id
     * @return the order
     */
    public Order getOrder(final int orderId) {
        return this.getSingleValue(OrderService.ORDER_BY_ID + orderId, Order.class);
    }

    /**
     * Gets the orders by username.
     *
     * @param username
     *            the username
     * @return the orders by username
     */
    public List<Order> getOrdersByUsername(final String username) {
        return this.getMultipleValues(OrderService.ORDERS_BY_USERNAME + username, Order.class);
    }

    /**
     * Gets the next id.
     *
     * @param name
     *            the name
     * @return the next id
     */
    public int getNextId(final String name) {
        return this.getSingleValue(OrderService.NEXT_ID, Integer.class);
    }

}
