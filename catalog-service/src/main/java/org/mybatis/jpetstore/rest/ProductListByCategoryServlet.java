/***************************************************************************
 * Copyright (C) 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package org.mybatis.jpetstore.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mybatis.jpetstore.domain.Product;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Reiner Jung
 *
 */
@WebServlet("/products-by-category")
public class ProductListByCategoryServlet extends AbstractCatalogServlet {

    private final static Logger LOG = Logger.getLogger(ProductListByCategoryServlet.class);

    /**
     *
     */
    private static final long serialVersionUID = 5615877008213075631L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final String categoryId = request.getParameter("categoryId");
        if (categoryId != null) {
            this.sendResult(response, this.service.getProductListByCategory(categoryId));
        } else {
            ProductListByCategoryServlet.LOG.error("no category specified");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        ProductListByCategoryServlet.LOG.error("no post requests permitted for ProductListByCategoryServlet");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void sendResult(final HttpServletResponse response, final List<Product> products) throws IOException {
        if (products == null) {
            ProductListByCategoryServlet.LOG.error("internal error, list should not be null");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), products);
        }
    }
}
