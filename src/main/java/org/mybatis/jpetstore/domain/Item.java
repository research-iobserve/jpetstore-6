/**
 *    Copyright 2010-2017 the original author or authors.
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
 * The Class Item.
 *
 * @author Eduardo Macarron
 */
public class Item implements Serializable {

    private static final long serialVersionUID = -2159121673445254631L;

    private String itemId;
    private String productId;
    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private int supplierId;
    private String status;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private Product product;
    private int quantity;

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(final String itemId) {
        this.itemId = itemId.trim();
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public int getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(final int supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getListPrice() {
        return this.listPrice;
    }

    public void setListPrice(final BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitCost() {
        return this.unitCost;
    }

    public void setUnitCost(final BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getAttribute1() {
        return this.attribute1;
    }

    public void setAttribute1(final String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return this.attribute2;
    }

    public void setAttribute2(final String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return this.attribute3;
    }

    public void setAttribute3(final String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return this.attribute4;
    }

    public void setAttribute4(final String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return this.attribute5;
    }

    public void setAttribute5(final String attribute5) {
        this.attribute5 = attribute5;
    }

    @Override
    public String toString() {
        return "(" + this.getItemId() + "-" + this.getProductId() + ")";
    }

}
