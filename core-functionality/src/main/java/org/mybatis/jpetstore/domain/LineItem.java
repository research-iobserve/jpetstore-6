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
 * The Class LineItem.
 *
 * @author Eduardo Macarron
 */
public class LineItem implements Serializable {

  private static final long serialVersionUID = 6804536240033522156L;

  private int orderId;
  private int lineNumber;
  private int quantity;
  private String itemId;
  private BigDecimal unitPrice;
  private Item item;
  private BigDecimal total;

  /**
   * Default constructor.
   */
  public LineItem() {
  }

  /**
   * Instantiates a new line item.
   *
   * @param lineNumber
   *            the line number
   * @param cartItem
   *            the cart item
   */
  public LineItem(final int lineNumber, final CartItem cartItem) {
    this.lineNumber = lineNumber;
    this.quantity = cartItem.getQuantity();
    this.itemId = cartItem.getItem().getItemId();
    this.unitPrice = cartItem.getItem().getListPrice();
    this.item = cartItem.getItem();
  }

  public int getOrderId() {
    return this.orderId;
  }

  public void setOrderId(final int orderId) {
    this.orderId = orderId;
  }

  public int getLineNumber() {
    return this.lineNumber;
  }

  public void setLineNumber(final int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public String getItemId() {
    return this.itemId;
  }

  public void setItemId(final String itemId) {
    this.itemId = itemId;
  }

  public BigDecimal getUnitPrice() {
    return this.unitPrice;
  }

  public void setUnitPrice(final BigDecimal unitprice) {
    this.unitPrice = unitprice;
  }

  public BigDecimal getTotal() {
    return this.total;
  }

  public Item getItem() {
    return this.item;
  }

  /**
   * Set the item for the list item and calculate the price.
   *
   * @param item
   *            item to be assigned
   */
  public void setItem(final Item item) {
    this.item = item;
    this.calculateTotal();
  }

  public int getQuantity() {
    return this.quantity;
  }

  /**
   * Set the quantity and calculate the price for this line item.
   *
   * @param quantity
   *            the quantity to be set
   */
  public void setQuantity(final int quantity) {
    this.quantity = quantity;
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
