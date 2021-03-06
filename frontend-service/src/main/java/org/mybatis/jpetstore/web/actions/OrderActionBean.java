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
package org.mybatis.jpetstore.web.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.service.OrderService;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * The Class OrderActionBean.
 *
 * @author Eduardo Macarron
 */
@SessionScope
public class OrderActionBean extends AbstractActionBean {

  private static final long serialVersionUID = -6171288227470176272L;

  private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/ConfirmOrder.jsp";
  private static final String LIST_ORDERS = "/WEB-INF/jsp/order/ListOrders.jsp";
  private static final String NEW_ORDER = "/WEB-INF/jsp/order/NewOrderForm.jsp";
  private static final String SHIPPING = "/WEB-INF/jsp/order/ShippingForm.jsp";
  private static final String VIEW_ORDER = "/WEB-INF/jsp/order/ViewOrder.jsp";

  private static final List<String> CARD_TYPE_LIST;

  @SpringBean
  private transient OrderService orderService;

  private Order order = new Order();
  private boolean shippingAddressRequired;
  private boolean confirmed;
  private List<Order> orderList;

  static {
    final List<String> cardList = new ArrayList<String>();
    cardList.add("Visa");
    cardList.add("MasterCard");
    cardList.add("American Express");
    CARD_TYPE_LIST = Collections.unmodifiableList(cardList);
  }

  public int getOrderId() {
    return this.order.getOrderId();
  }

  /**
   * Set the order id for the present order.
   *
   * @param orderId
   *            the id
   */
  public void setOrderId(final int orderId) {
    this.order.setOrderId(orderId);
  }

  public Order getOrder() {
    return this.order;
  }

  public void setOrder(final Order order) {
    this.order = order;
  }

  public boolean isShippingAddressRequired() {
    return this.shippingAddressRequired;
  }

  public void setShippingAddressRequired(final boolean shippingAddressRequired) {
    this.shippingAddressRequired = shippingAddressRequired;
  }

  public boolean isConfirmed() {
    return this.confirmed;
  }

  public void setConfirmed(final boolean confirmed) {
    this.confirmed = confirmed;
  }

  public List<String> getCreditCardTypes() {
    return OrderActionBean.CARD_TYPE_LIST;
  }

  public List<Order> getOrderList() {
    return this.orderList;
  }

  /**
   * List orders.
   *
   * @return the resolution
   */
  public Resolution listOrders() {
    final HttpSession session = this.context.getRequest().getSession();
    final AccountActionBean accountBean = (AccountActionBean) session.getAttribute("/actions/Account.action");
    this.orderList = this.orderService.getOrdersByUsername(accountBean.getAccount().getUsername());
    return new ForwardResolution(OrderActionBean.LIST_ORDERS);
  }

  /**
   * New order form.
   *
   * @return the resolution
   */
  public Resolution newOrderForm() {
    final HttpSession session = this.context.getRequest().getSession();
    final AccountActionBean accountBean = (AccountActionBean) session.getAttribute("/actions/Account.action");
    final CartActionBean cartBean = (CartActionBean) session.getAttribute("/actions/Cart.action");

    this.clear();
    if ((accountBean == null) || !accountBean.isAuthenticated()) {
      this.setMessage("You must sign on before attempting to check out.  Please sign on and try checking out again.");
      return new ForwardResolution(AccountActionBean.class);
    } else if (cartBean != null) {
      this.order.initOrder(accountBean.getAccount(), cartBean.getCart());
      return new ForwardResolution(OrderActionBean.NEW_ORDER);
    } else {
      this.setMessage("An order could not be created because a cart could not be found.");
      return new ForwardResolution(AbstractActionBean.ERROR);
    }
  }

  /**
   * New order.
   *
   * @return the resolution
   */
  public Resolution newOrder() {
    final HttpSession session = this.context.getRequest().getSession();

    if (this.shippingAddressRequired) {
      this.shippingAddressRequired = false;
      return new ForwardResolution(OrderActionBean.SHIPPING);
    } else if (!this.isConfirmed()) {
      return new ForwardResolution(OrderActionBean.CONFIRM_ORDER);
    } else if (this.getOrder() != null) {

      this.orderService.insertOrder(this.order);

      final CartActionBean cartBean = (CartActionBean) session.getAttribute("/actions/Cart.action");
      cartBean.clear();

      this.setMessage("Thank you, your order has been submitted.");

      return new ForwardResolution(OrderActionBean.VIEW_ORDER);
    } else {
      this.setMessage("An error occurred processing your order (order was null).");
      return new ForwardResolution(AbstractActionBean.ERROR);
    }
  }

  /**
   * View order.
   *
   * @return the resolution
   */
  public Resolution viewOrder() {
    final HttpSession session = this.context.getRequest().getSession();

    final AccountActionBean accountBean = (AccountActionBean) session.getAttribute("accountBean");

    this.order = this.orderService.getOrder(this.order.getOrderId());

    if (accountBean.getAccount().getUsername().equals(this.order.getUsername())) {
      return new ForwardResolution(OrderActionBean.VIEW_ORDER);
    } else {
      this.order = null;
      this.setMessage("You may only view your own orders.");
      return new ForwardResolution(AbstractActionBean.ERROR);
    }
  }

  /**
   * Clear.
   */
  public void clear() {
    this.order = new Order();
    this.shippingAddressRequired = false;
    this.confirmed = false;
    this.orderList = null;
  }

}
