/**
 *    Copyright (C) 2010-2017 the original author or authors.
 *                  2018 iObserve Project (https://www.iobserve-devops.net)
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
        return categoryMapper.getCategoryList();
    }

    public Category getCategory(final String categoryId) {
        return categoryMapper.getCategory(categoryId);
    }

    public Product getProduct(final String productId) {
        return productMapper.getProduct(productId);
    }

    public List<Product> getProductListByCategory(final String categoryId) {
        return productMapper.getProductListByCategory(categoryId);
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
            products.addAll(productMapper.searchProductList("%" + keyword.toLowerCase() + "%"));
        }
        return products;
    }

    public List<Item> getItemListByProduct(final String productId) {
        return itemMapper.getItemListByProduct(productId);
    }

    public Item getItem(final String itemId) {
        return itemMapper.getItem(itemId);
    }

    public boolean isItemInStock(final String itemId) {
        return itemMapper.getInventoryQuantity(itemId) > 0;
    }
}