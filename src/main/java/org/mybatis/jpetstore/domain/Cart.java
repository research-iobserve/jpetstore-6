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
package org.mybatis.jpetstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The Class Cart.
 *
 * @author Eduardo Macarron
 */
public class Cart implements Serializable {

  private static final long serialVersionUID = 8329559983943337176L;

  private final Map<String, CartItem> itemMap = Collections.synchronizedMap(new HashMap<String, CartItem>());
  private final List<CartItem> itemList = new ArrayList<CartItem>();

  public Iterator<CartItem> getCartItems() {
    return this.itemList.iterator();
  }

  public List<CartItem> getCartItemList() {
    return this.itemList;
  }

  public int getNumberOfItems() {
    return this.itemList.size();
  }

  public Iterator<CartItem> getAllCartItems() {
    return this.itemList.iterator();
  }

  /**
   * Checks whether an item for the specified key exists.
   *
   * @param itemId
   *            item id
   * @return true if the item exists
   */
  public boolean containsItemId(final String itemId) {
    return this.itemMap.containsKey(itemId);
  }

  /**
   * Adds the item.
   *
   * @param item
   *            the item
   * @param isInStock
   *            the is in stock
   */
  public void addItem(final Item item, final boolean isInStock) {
    CartItem cartItem = this.itemMap.get(item.getItemId());
    if (cartItem == null) {
      cartItem = new CartItem();
      cartItem.setItem(item);
      cartItem.setQuantity(0);
      cartItem.setInStock(isInStock);
      this.itemMap.put(item.getItemId(), cartItem);
      this.itemList.add(cartItem);
    }
    cartItem.incrementQuantity();
  }

  /**
   * Removes the item by id.
   *
   * @param itemId
   *            the item id
   * @return the item
   */
  public Item removeItemById(final String itemId) {
    final CartItem cartItem = this.itemMap.remove(itemId);
    if (cartItem == null) {
      return null;
    } else {
      this.itemList.remove(cartItem);
      return cartItem.getItem();
    }
  }

  /**
   * Increment quantity by item id.
   *
   * @param itemId
   *            the item id
   */
  public void incrementQuantityByItemId(final String itemId) {
    final CartItem cartItem = this.itemMap.get(itemId);
    cartItem.incrementQuantity();
  }

  /**
   * Set the quantity of an item by the item id.
   *
   * @param itemId
   *            the item id
   * @param quantity
   *            the quantity for that item
   */
  public void setQuantityByItemId(final String itemId, final int quantity) {
    final CartItem cartItem = this.itemMap.get(itemId);
    cartItem.setQuantity(quantity);
  }

  /**
   * Gets the sub total.
   *
   * @return the sub total
   */
  public BigDecimal getSubTotal() {
    BigDecimal subTotal = new BigDecimal("0");
    final Iterator<CartItem> items = this.getAllCartItems();
    while (items.hasNext()) {
      final CartItem cartItem = items.next();
      final Item item = cartItem.getItem();
      final BigDecimal listPrice = item.getListPrice();
      final BigDecimal quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
      subTotal = subTotal.add(listPrice.multiply(quantity));
    }
    return subTotal;
  }

}
