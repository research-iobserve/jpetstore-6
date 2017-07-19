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

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.jpetstore.domain.Cart;
import org.mybatis.jpetstore.domain.CartItem;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.service.CatalogService;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * The Class CartActionBean.
 *
 * @author Eduardo Macarron
 */
@SessionScope
public class CartActionBean extends AbstractActionBean {

  private static final long serialVersionUID = -4038684592582714235L;

  private static final String VIEW_CART = "/WEB-INF/jsp/cart/Cart.jsp";
  private static final String CHECK_OUT = "/WEB-INF/jsp/cart/Checkout.jsp";

  @SpringBean
  private transient CatalogService catalogService;

  private Cart cart = new Cart();
  private String workingItemId;

  public Cart getCart() {
    return this.cart;
  }

  public void setCart(final Cart cart) {
    this.cart = cart;
  }

  public void setWorkingItemId(final String workingItemId) {
    this.workingItemId = workingItemId;
  }

  /**
   * Adds the item to cart.
   *
   * @return the resolution
   */
  public Resolution addItemToCart() {
    if (this.cart.containsItemId(this.workingItemId)) {
      this.cart.incrementQuantityByItemId(this.workingItemId);
    } else {
      // isInStock is a "real-time" property that must be updated
      // every time an item is added to the cart, even if other
      // item details are cached.
      final boolean isInStock = this.catalogService.isItemInStock(this.workingItemId);
      final Item item = this.catalogService.getItem(this.workingItemId);
      this.cart.addItem(item, isInStock);
    }

    return new ForwardResolution(CartActionBean.VIEW_CART);
  }

  /**
   * Removes the item from cart.
   *
   * @return the resolution
   */
  public Resolution removeItemFromCart() {

    final Item item = this.cart.removeItemById(this.workingItemId);

    if (item == null) {
      this.setMessage("Attempted to remove null CartItem from Cart.");
      return new ForwardResolution(AbstractActionBean.ERROR);
    } else {
      return new ForwardResolution(CartActionBean.VIEW_CART);
    }
  }

  /**
   * Update cart quantities.
   *
   * @return the resolution
   */
  public Resolution updateCartQuantities() {
    final HttpServletRequest request = this.context.getRequest();

    final Iterator<CartItem> cartItems = this.getCart().getAllCartItems();
    while (cartItems.hasNext()) {
      final CartItem cartItem = cartItems.next();
      final String itemId = cartItem.getItem().getItemId();
      try {
        final int quantity = Integer.parseInt(request.getParameter(itemId));
        this.getCart().setQuantityByItemId(itemId, quantity);
        if (quantity < 1) {
          cartItems.remove();
        }
      } catch (final Exception e) {
        // ignore parse exceptions on purpose
      }
    }

    return new ForwardResolution(CartActionBean.VIEW_CART);
  }

  /**
   * View cart.
   *
   * @return forward resolution for cart
   */
  public ForwardResolution viewCart() {
    return new ForwardResolution(CartActionBean.VIEW_CART);
  }

  /**
   * goto check out.
   *
   * @return forward resolution for check out
   */
  public ForwardResolution checkOut() {
    return new ForwardResolution(CartActionBean.CHECK_OUT);
  }

  /**
   * Clear cart and stay on page.
   */
  public void clear() {
    this.cart = new Cart();
    this.workingItemId = null;
  }

}
