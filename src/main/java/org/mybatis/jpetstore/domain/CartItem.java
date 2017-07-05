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

/**
 * The Class CartItem.
 *
 * @author Eduardo Macarron
 */
public class CartItem implements Serializable {

  private static final long serialVersionUID = 6620528781626504362L;

  private Item item;
  private int quantity;
  private boolean inStock;
  private BigDecimal total;

  public boolean isInStock() {
    return this.inStock;
  }

  public void setInStock(final boolean inStock) {
    this.inStock = inStock;
  }

  public BigDecimal getTotal() {
    return this.total;
  }

  public Item getItem() {
    return this.item;
  }

  /**
   * Assign an item to a cart item and calculate its price.
   *
   * @param item
   *            the item to be assigned
   */
  public void setItem(final Item item) {
    this.item = item;
    this.calculateTotal();
  }

  /**
   * Get the quantity of items in the cart.
   *
   * @return the quantity
   */
  public int getQuantity() {
    return this.quantity;
  }

  /**
   * Set the quantity and calculate the price for this cart item.
   *
   * @param quantity
   *            the quantity to be set
   */
  public void setQuantity(final int quantity) {
    this.quantity = quantity;
    this.calculateTotal();
  }

  /**
   * Increase quantity.
   */
  public void incrementQuantity() {
    this.quantity++;
    this.calculateTotal();
  }

  private void calculateTotal() {
    if ((this.item != null) && (this.item.getListPrice() != null)) {
      this.total = this.item.getListPrice().multiply(new BigDecimal(this.quantity));
    } else {
      this.total = null;
    }
  }

}
