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
package org.mybatis.jpetstore.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.mapper.ICategoryMapper;
import org.mybatis.jpetstore.mapper.IItemMapper;
import org.mybatis.jpetstore.mapper.IProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class CatalogService.
 *
 * @author Eduardo Macarron
 */
@Service
public class CatalogService {

  @Autowired
  private ICategoryMapper categoryMapper;
  @Autowired
  private IItemMapper itemMapper;
  @Autowired
  private IProductMapper productMapper;

  public List<Category> getCategoryList() {
    return this.categoryMapper.getCategoryList();
  }

  /**
   * Get category by id.
   *
   * @param categoryId
   *            the id
   * @return category or null when missing
   */
  public Category getCategory(final String categoryId) {
    return this.categoryMapper.getCategory(categoryId);
  }

  /**
   * Get a product by id.
   *
   * @param productId
   *            the id
   * @return product or null when missing
   */
  public Product getProduct(final String productId) {
    return this.productMapper.getProduct(productId);
  }

  /**
   * Get a list of products based on their category.
   *
   * @param categoryId
   *            the category id
   * @return list of products
   */
  public List<Product> getProductListByCategory(final String categoryId) {
    return this.productMapper.getProductListByCategory(categoryId);
  }

  /**
   * Search product list.
   *
   * @param keywords
   *            the keywords
   * @return the list
   */
  public List<Product> searchProductList(final String keywords) {
    final List<Product> products = new ArrayList<Product>();
    for (final String keyword : keywords.split("\\s+")) {
      products.addAll(this.productMapper.searchProductList("%" + keyword.toLowerCase() + "%"));
    }
    return products;
  }

  /**
   * Get list of items for a product.
   *
   * @param productId
   *            product id
   * @return item list
   */
  public List<Item> getItemListByProduct(final String productId) {
    return this.itemMapper.getItemListByProduct(productId);
  }

  /**
   * Get a single item by id.
   *
   * @param itemId
   *            item id
   * @return get item or null when missing
   */
  public Item getItem(final String itemId) {
    return this.itemMapper.getItem(itemId);
  }

  /**
   * Check whether item is available in inventory.
   * 
   * @param itemId
   *            item id
   * @return true on success else false
   */
  public boolean isItemInStock(final String itemId) {
    return this.itemMapper.getInventoryQuantity(itemId) > 0;
  }
}