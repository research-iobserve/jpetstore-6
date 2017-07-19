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

/**
 * The Class Product.
 *
 * @author Eduardo Macarron
 */
public class Product implements Serializable {

  private static final long serialVersionUID = -7492639752670189553L;

  private String productId;
  private String categoryId;
  private String name;
  private String description;

  public String getProductId() {
    return this.productId;
  }

  public void setProductId(final String productId) {
    this.productId = productId.trim();
  }

  public String getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(final String categoryId) {
    this.categoryId = categoryId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return this.getName();
  }

}
