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
package org.mybatis.jpetstore.mapper;

import java.util.List;

import org.mybatis.jpetstore.domain.LineItem;

/**
 * The Interface LineItemMapper.
 *
 * @author Eduardo Macarron
 */
public interface ILineItemMapper {

  /**
   * Get line items for an order.
   *
   * @param orderId
   *            order id
   * @return list of items
   */
  List<LineItem> getLineItemsByOrderId(int orderId);

  /**
   * Insert an item.
   *
   * @param lineItem
   *            the item to be inserted
   */
  void insertLineItem(LineItem lineItem);

}
