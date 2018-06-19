/**
 *    Copyright (C) 2010-2017 the original author or authors.
 *                  2018 iObserve Project (https://www.iobserve-devops.net)
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

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.LineItem;
import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.mapper.IItemMapper;
import org.mybatis.jpetstore.mapper.ILineItemMapper;
import org.mybatis.jpetstore.mapper.IOrderMapper;

/**
 * @author coderliux
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private IItemMapper itemMapper;
    @Mock
    private IOrderMapper orderMapper;
    @Mock
    private ILineItemMapper lineItemMapper;

    @InjectMocks
    private OrderService orderService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldReturnOrderWhenGivenOrderIdWithOutLineItems() {
        // given
        final int orderId = 1;
        final Order order = new Order();
        final List<LineItem> lineItems = new ArrayList<LineItem>();

        // when
        Mockito.when(orderMapper.getOrder(orderId)).thenReturn(order);
        Mockito.when(lineItemMapper.getLineItemsByOrderId(orderId)).thenReturn(lineItems);

        // then
        Assertions.assertThat(orderService.getOrder(orderId)).isEqualTo(order);
        Assertions.assertThat(orderService.getOrder(orderId).getLineItems()).isEmpty();
    }

    @Test
    public void shouldReturnOrderWhenGivenOrderIdExistedLineItems() {
        // given
        final int orderId = 1;
        final Order order = new Order();
        final List<LineItem> lineItems = new ArrayList<LineItem>();
        final LineItem item = new LineItem();
        final String itemId = "abc";
        item.setItemId(itemId);
        lineItems.add(item);

        // when
        Mockito.when(orderMapper.getOrder(orderId)).thenReturn(order);
        Mockito.when(lineItemMapper.getLineItemsByOrderId(orderId)).thenReturn(lineItems);
        Mockito.when(itemMapper.getItem(itemId)).thenReturn(new Item());
        Mockito.when(itemMapper.getInventoryQuantity(itemId)).thenReturn(new Integer(5));

        // then
        final Order expectedOrder = orderService.getOrder(orderId);
        Assertions.assertThat(expectedOrder).isEqualTo(order);
        Assertions.assertThat(expectedOrder.getLineItems()).hasSize(1);
        Assertions.assertThat(expectedOrder.getLineItems().get(0).getItem().getQuantity()).isEqualTo(5);
    }

}