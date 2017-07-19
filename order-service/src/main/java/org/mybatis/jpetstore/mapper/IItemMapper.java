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
import java.util.Map;

import org.mybatis.jpetstore.domain.Item;

/**
 * The Interface ItemMapper.
 *
 * @author Eduardo Macarron
 */
public interface IItemMapper {

  /**
   * Update inventory.
   *
   * @param param
   *            map of values
   */
  void updateInventoryQuantity(Map<String, Object> param);

  /**
   * Get quantity of an item in the inventory.
   *
   * @param itemId
   *            item id
   * @return the quantity
   */
  int getInventoryQuantity(String itemId);

  /**
   * Get items for a specific product.
   *
   * @param productId
   *            product id
   * @return list of items
   */
  List<Item> getItemListByProduct(String productId);

  /**
   * Get a specific item.
   * 
   * @param itemId
   *            id of the item
   * @return the item or null
   */
  Item getItem(String itemId);

}
