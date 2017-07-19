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

import java.util.List;

import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.service.CatalogService;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * The Class CatalogActionBean.
 *
 * @author Eduardo Macarron
 */
@SessionScope
public class CatalogActionBean extends AbstractActionBean {

  private static final long serialVersionUID = 5849523372175050635L;

  private static final String MAIN = "/WEB-INF/jsp/catalog/Main.jsp";
  private static final String VIEW_CATEGORY = "/WEB-INF/jsp/catalog/Category.jsp";
  private static final String VIEW_PRODUCT = "/WEB-INF/jsp/catalog/Product.jsp";
  private static final String VIEW_ITEM = "/WEB-INF/jsp/catalog/Item.jsp";
  private static final String SEARCH_PRODUCTS = "/WEB-INF/jsp/catalog/SearchProducts.jsp";

  @SpringBean
  private transient CatalogService catalogService;

  private String keyword;

  private String categoryId;
  private Category category;
  private List<Category> categoryList;

  private String productId;
  private Product product;
  private List<Product> productList;

  private String itemId;
  private Item item;
  private List<Item> itemList;

  public String getKeyword() {
    return this.keyword;
  }

  public void setKeyword(final String keyword) {
    this.keyword = keyword;
  }

  public String getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(final String categoryId) {
    this.categoryId = categoryId;
  }

  public String getProductId() {
    return this.productId;
  }

  public void setProductId(final String productId) {
    this.productId = productId;
  }

  public String getItemId() {
    return this.itemId;
  }

  public void setItemId(final String itemId) {
    this.itemId = itemId;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(final Category category) {
    this.category = category;
  }

  public Product getProduct() {
    return this.product;
  }

  public void setProduct(final Product product) {
    this.product = product;
  }

  public Item getItem() {
    return this.item;
  }

  public void setItem(final Item item) {
    this.item = item;
  }

  public List<Category> getCategoryList() {
    return this.categoryList;
  }

  public void setCategoryList(final List<Category> categoryList) {
    this.categoryList = categoryList;
  }

  public List<Product> getProductList() {
    return this.productList;
  }

  public void setProductList(final List<Product> productList) {
    this.productList = productList;
  }

  public List<Item> getItemList() {
    return this.itemList;
  }

  public void setItemList(final List<Item> itemList) {
    this.itemList = itemList;
  }

  /**
   * Default handler forwards to Main.jsp.
   *
   * @return resultion
   */
  @DefaultHandler
  public ForwardResolution viewMain() {
    return new ForwardResolution(CatalogActionBean.MAIN);
  }

  /**
   * View category.
   *
   * @return the forward resolution
   */
  public ForwardResolution viewCategory() {
    if (this.categoryId != null) {
      this.productList = this.catalogService.getProductListByCategory(this.categoryId);
      this.category = this.catalogService.getCategory(this.categoryId);
    }
    return new ForwardResolution(CatalogActionBean.VIEW_CATEGORY);
  }

  /**
   * View product.
   *
   * @return the forward resolution
   */
  public ForwardResolution viewProduct() {
    if (this.productId != null) {
      this.itemList = this.catalogService.getItemListByProduct(this.productId);
      this.product = this.catalogService.getProduct(this.productId);
    }
    return new ForwardResolution(CatalogActionBean.VIEW_PRODUCT);
  }

  /**
   * View item.
   *
   * @return the forward resolution
   */
  public ForwardResolution viewItem() {
    this.item = this.catalogService.getItem(this.itemId);
    this.product = this.item.getProduct();
    return new ForwardResolution(CatalogActionBean.VIEW_ITEM);
  }

  /**
   * Search products.
   *
   * @return the forward resolution
   */
  public ForwardResolution searchProducts() {
    if ((this.keyword == null) || (this.keyword.length() < 1)) {
      this.setMessage("Please enter a keyword to search for, then press the search button.");
      return new ForwardResolution(AbstractActionBean.ERROR);
    } else {
      this.productList = this.catalogService.searchProductList(this.keyword.toLowerCase());
      return new ForwardResolution(CatalogActionBean.SEARCH_PRODUCTS);
    }
  }

  /**
   * Clear.
   */
  public void clear() {
    this.keyword = null;

    this.categoryId = null;
    this.category = null;
    this.categoryList = null;

    this.productId = null;
    this.product = null;
    this.productList = null;

    this.itemId = null;
    this.item = null;
    this.itemList = null;
  }

}
