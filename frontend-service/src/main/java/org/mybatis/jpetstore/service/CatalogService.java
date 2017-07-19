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

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Product;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * The Class CatalogService.
 *
 * @author Eduardo Macarron
 */
@Service
public class CatalogService {

    private final static Logger LOG = Logger.getLogger(CatalogService.class);

    private static final String CATALOG_SERVICE = "http://172.17.0.2:8080/jpetstore-catalog/";

    private static final String GET_CATEGORY = "category?categoryId=";

    private static final String GET_CATEGORIES = "categories";

    private static final String GET_PRODUCT = "product?productId=";

    private static final String GET_PRODUCT_LIST_BY_CATEGORY = "products-by-category?categoryId=";

    private static final String GET_SEARCH_PRODUCTS = "search-products?keywords=";

    private static final String GET_ITEMS_BY_PRODUCT = "items-by-product?productId=";

    private static final String GET_ITEM = "item?itemId=";

    private static final String IS_ITEM_IN_STOCK = "item-in-stock?itemId=";

    public List<Category> getCategoryList() {
        return this.getMultipleValues(CatalogService.CATALOG_SERVICE + CatalogService.GET_CATEGORIES, Category.class);
    }

    /**
     * Get category by id.
     *
     * @param categoryId
     *            the id
     * @return category or null when missing
     */
    public Category getCategory(final String categoryId) {
        return this.getSingleValue(CatalogService.CATALOG_SERVICE + CatalogService.GET_CATEGORY + categoryId,
                Category.class);
    }

    /**
     * Get a product by id.
     *
     * @param productId
     *            the id
     * @return product or null when missing
     */
    public Product getProduct(final String productId) {
        return this.getSingleValue(CatalogService.CATALOG_SERVICE + CatalogService.GET_PRODUCT + productId,
                Product.class);
    }

    /**
     * Get a list of products based on their category.
     *
     * @param categoryId
     *            the category id
     * @return list of products
     */
    public List<Product> getProductListByCategory(final String categoryId) {
        return this.getMultipleValues(
                CatalogService.CATALOG_SERVICE + CatalogService.GET_PRODUCT_LIST_BY_CATEGORY + categoryId,
                Product.class);
    }

    /**
     * Search product list.
     *
     * @param keywords
     *            the keywords
     * @return the list
     */
    public List<Product> searchProductList(final String keywords) {
        return this.getMultipleValues(CatalogService.CATALOG_SERVICE + CatalogService.GET_SEARCH_PRODUCTS + keywords,
                Product.class);
    }

    /**
     * Get list of items for a product.
     *
     * @param productId
     *            product id
     * @return item list
     */
    public List<Item> getItemListByProduct(final String productId) {
        return this.getMultipleValues(CatalogService.CATALOG_SERVICE + CatalogService.GET_ITEMS_BY_PRODUCT + productId,
                Item.class);
    }

    /**
     * Get a single item by id.
     *
     * @param itemId
     *            item id
     * @return get item or null when missing
     */
    public Item getItem(final String itemId) {
        return this.getSingleValue(CatalogService.CATALOG_SERVICE + CatalogService.GET_ITEM + itemId, Item.class);
    }

    /**
     * Check whether item is available in inventory.
     *
     * @param itemId
     *            item id
     * @return true on success else false
     */
    public boolean isItemInStock(final String itemId) {
        return this.getSingleValue(CatalogService.CATALOG_SERVICE + CatalogService.IS_ITEM_IN_STOCK + itemId,
                Boolean.class);
    }

    private <T> T getSingleValue(final String url, final Class<T> clazz) {
        try {
            CatalogService.LOG.info("get from remote " + url);
            final NetHttpTransport httpTransport = new NetHttpTransport();
            final HttpRequest request = httpTransport.createRequestFactory().buildGetRequest(new GenericUrl(url));
            final HttpResponse response = request.execute();

            if (response.getStatusCode() == 200) {
                final ObjectMapper mapper = new ObjectMapper();
                final T object = mapper.readValue(response.getContent(), clazz);

                return object;
            } else {
                return null;
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> List<T> getMultipleValues(final String url, final Class<T> clazz) {
        try {
            CatalogService.LOG.info("get from remote " + url);
            final NetHttpTransport httpTransport = new NetHttpTransport();
            final HttpRequest request = httpTransport.createRequestFactory().buildGetRequest(new GenericUrl(url));
            final HttpResponse response = request.execute();

            if (response.getStatusCode() == 200) {
                final ObjectMapper mapper = new ObjectMapper();
                final List<T> list = mapper.readValue(response.getContent(),
                        mapper.getTypeFactory().constructCollectionType(List.class, clazz));

                return list;
            } else {
                return null;
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}