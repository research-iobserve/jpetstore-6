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

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.mybatis.jpetstore.domain.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * The Class OrderService.
 *
 * @author Eduardo Macarron
 */
@Service
public class OrderService {
	
	private final static Logger LOG = Logger.getLogger(CatalogService.class);
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

	private <T> T getSingleValue(final String url, final Class<T> clazz) {
		try {
			LOG.info("get from remote " + url);
			final NetHttpTransport httpTransport = new NetHttpTransport();
			final HttpRequest request = httpTransport.createRequestFactory().buildGetRequest(new GenericUrl(url));
			final HttpResponse response = request.execute();

			if (response.getStatusCode() == 200) {
				final ObjectMapper mapper = new ObjectMapper();
				final T object = mapper.readValue(response.getContent(), clazz);

				return object;
			} else {
				return null;
			}
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private <T> List<T> getMultipleValues(final String url, final Class<T> clazz) {
		try {
			LOG.info("get from remote " + url);
			final NetHttpTransport httpTransport = new NetHttpTransport();
			final HttpRequest request = httpTransport.createRequestFactory().buildGetRequest(new GenericUrl(url));
			final HttpResponse response = request.execute();

			if (response.getStatusCode() == 200) {
				final ObjectMapper mapper = new ObjectMapper();
				final List<T> list = mapper.readValue(response.getContent(),
						mapper.getTypeFactory().constructCollectionType(List.class, clazz));

				return list;
			} else {
				return null;
			}
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * Post an object.
     * 
     * @param url where
     * @param object the object
     * @return result code
     */
    private <T> int postOperation(final String url, final T object) {      
        StringWriter sw = new StringWriter();
        
        final ObjectMapper mapper = new ObjectMapper();
        try {
			mapper.writeValue(sw, object);
						
			HttpClient httpClient = HttpClientBuilder.create().build(); 

	        try {
	            HttpPost request = new HttpPost(url);
	            StringEntity params = new StringEntity(sw.toString());
	            request.addHeader("content-type", "application/json");
	            request.setEntity(params);
	            org.apache.http.HttpResponse response = httpClient.execute(request);

	            return response.getStatusLine().getStatusCode();
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        	return 404;
	        }
		} catch (IOException e) {
			e.printStackTrace();
			return 404;
		}
    }

}
