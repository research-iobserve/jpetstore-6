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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * The Class Order.
 *
 * @author Eduardo Macarron
 */
public class Order implements Serializable {

  private static final long serialVersionUID = 6321792448424424931L;

  private int orderId;
  private String username;
  private Date orderDate;
  private String shipAddress1;
  private String shipAddress2;
  private String shipCity;
  private String shipState;
  private String shipZip;
  private String shipCountry;
  private String billAddress1;
  private String billAddress2;
  private String billCity;
  private String billState;
  private String billZip;
  private String billCountry;
  private String courier;
  private BigDecimal totalPrice;
  private String billToFirstName;
  private String billToLastName;
  private String shipToFirstName;
  private String shipToLastName;
  private String creditCard;
  private String expiryDate;
  private String cardType;
  private String locale;
  private String status;
  private List<LineItem> lineItems = new ArrayList<LineItem>();

  public int getOrderId() {
    return this.orderId;
  }

  public void setOrderId(final int orderId) {
    this.orderId = orderId;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public Date getOrderDate() {
    return this.orderDate;
  }

  public void setOrderDate(final Date orderDate) {
    this.orderDate = orderDate;
  }

  public String getShipAddress1() {
    return this.shipAddress1;
  }

  public void setShipAddress1(final String shipAddress1) {
    this.shipAddress1 = shipAddress1;
  }

  public String getShipAddress2() {
    return this.shipAddress2;
  }

  public void setShipAddress2(final String shipAddress2) {
    this.shipAddress2 = shipAddress2;
  }

  public String getShipCity() {
    return this.shipCity;
  }

  public void setShipCity(final String shipCity) {
    this.shipCity = shipCity;
  }

  public String getShipState() {
    return this.shipState;
  }

  public void setShipState(final String shipState) {
    this.shipState = shipState;
  }

  public String getShipZip() {
    return this.shipZip;
  }

  public void setShipZip(final String shipZip) {
    this.shipZip = shipZip;
  }

  public String getShipCountry() {
    return this.shipCountry;
  }

  public void setShipCountry(final String shipCountry) {
    this.shipCountry = shipCountry;
  }

  public String getBillAddress1() {
    return this.billAddress1;
  }

  public void setBillAddress1(final String billAddress1) {
    this.billAddress1 = billAddress1;
  }

  public String getBillAddress2() {
    return this.billAddress2;
  }

  public void setBillAddress2(final String billAddress2) {
    this.billAddress2 = billAddress2;
  }

  public String getBillCity() {
    return this.billCity;
  }

  public void setBillCity(final String billCity) {
    this.billCity = billCity;
  }

  public String getBillState() {
    return this.billState;
  }

  public void setBillState(final String billState) {
    this.billState = billState;
  }

  public String getBillZip() {
    return this.billZip;
  }

  public void setBillZip(final String billZip) {
    this.billZip = billZip;
  }

  public String getBillCountry() {
    return this.billCountry;
  }

  public void setBillCountry(final String billCountry) {
    this.billCountry = billCountry;
  }

  public String getCourier() {
    return this.courier;
  }

  public void setCourier(final String courier) {
    this.courier = courier;
  }

  public BigDecimal getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(final BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getBillToFirstName() {
    return this.billToFirstName;
  }

  public void setBillToFirstName(final String billToFirstName) {
    this.billToFirstName = billToFirstName;
  }

  public String getBillToLastName() {
    return this.billToLastName;
  }

  public void setBillToLastName(final String billToLastName) {
    this.billToLastName = billToLastName;
  }

  public String getShipToFirstName() {
    return this.shipToFirstName;
  }

  public void setShipToFirstName(final String shipFoFirstName) {
    this.shipToFirstName = shipFoFirstName;
  }

  public String getShipToLastName() {
    return this.shipToLastName;
  }

  public void setShipToLastName(final String shipToLastName) {
    this.shipToLastName = shipToLastName;
  }

  public String getCreditCard() {
    return this.creditCard;
  }

  public void setCreditCard(final String creditCard) {
    this.creditCard = creditCard;
  }

  public String getExpiryDate() {
    return this.expiryDate;
  }

  public void setExpiryDate(final String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public String getCardType() {
    return this.cardType;
  }

  public void setCardType(final String cardType) {
    this.cardType = cardType;
  }

  public String getLocale() {
    return this.locale;
  }

  public void setLocale(final String locale) {
    this.locale = locale;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  public void setLineItems(final List<LineItem> lineItems) {
    this.lineItems = lineItems;
  }

  public List<LineItem> getLineItems() {
    return this.lineItems;
  }

  /**
   * Inits the order.
   *
   * @param account
   *            the account
   * @param cart
   *            the cart
   */
  public void initOrder(final Account account, final Cart cart) {

    this.username = account.getUsername();
    this.orderDate = new Date();

    this.shipToFirstName = account.getFirstName();
    this.shipToLastName = account.getLastName();
    this.shipAddress1 = account.getAddress1();
    this.shipAddress2 = account.getAddress2();
    this.shipCity = account.getCity();
    this.shipState = account.getState();
    this.shipZip = account.getZip();
    this.shipCountry = account.getCountry();

    this.billToFirstName = account.getFirstName();
    this.billToLastName = account.getLastName();
    this.billAddress1 = account.getAddress1();
    this.billAddress2 = account.getAddress2();
    this.billCity = account.getCity();
    this.billState = account.getState();
    this.billZip = account.getZip();
    this.billCountry = account.getCountry();

    this.totalPrice = cart.getSubTotal();

    this.creditCard = "999 9999 9999 9999";
    this.expiryDate = "12/03";
    this.cardType = "Visa";
    this.courier = "UPS";
    this.locale = "CA";
    this.status = "P";

    final Iterator<CartItem> i = cart.getAllCartItems();
    while (i.hasNext()) {
      final CartItem cartItem = i.next();
      this.addLineItem(cartItem);
    }

  }

  /**
   * Add a cart item to an order which converts a cart item to a line item.
   *
   * @param cartItem
   *            the cart item to convert
   */
  public void addLineItem(final CartItem cartItem) {
    final LineItem lineItem = new LineItem(this.lineItems.size() + 1, cartItem);
    this.addLineItem(lineItem);
  }

  /**
   * Add a already generated line item to the item list.
   *
   * @param lineItem
   *            the line item.
   */
  public void addLineItem(final LineItem lineItem) {
    this.lineItems.add(lineItem);
  }

}
