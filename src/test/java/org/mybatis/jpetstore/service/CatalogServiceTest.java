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

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.mapper.IProductMapper;

/**
 * @author Eduardo Macarron
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CatalogServiceTest {

    @Mock
    private IProductMapper productMapper;

    @InjectMocks
    private CatalogService catalogService;

    @Test
    public void shouldCallTheSearchMapperTwice() {
        // given
        final String keywords = "a b";
        final List<Product> l1 = new ArrayList<Product>();
        l1.add(new Product());
        final List<Product> l2 = new ArrayList<Product>();
        l2.add(new Product());

        // when
        Mockito.when(productMapper.searchProductList("%a%")).thenReturn(l1);
        Mockito.when(productMapper.searchProductList("%b%")).thenReturn(l2);
        final List<Product> r = catalogService.searchProductList(keywords);

        // then
        Assertions.assertThat(r).hasSize(2);
        Assertions.assertThat(r.get(0)).isSameAs(l1.get(0));
        Assertions.assertThat(r.get(1)).isSameAs(l2.get(0));
    }

}