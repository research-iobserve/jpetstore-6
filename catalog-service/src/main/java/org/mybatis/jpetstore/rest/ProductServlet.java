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
@WebServlet("/product")
public class ProductServlet extends AbstractCatalogServlet {

    /**
     *
     */
    private static final long serialVersionUID = 2738762076073852467L;

    private final static Logger LOG = Logger.getLogger(ProductServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String productId = request.getParameter("productId");
        if (productId != null) {
            this.sendResult(response, this.service.getProduct(productId));
        } else {
            ProductServlet.LOG.error("no item id specified");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        ProductServlet.LOG.error("no post requests permitted for CategoryServlet");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void sendResult(final HttpServletResponse response, final Product product) throws IOException {
        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), product);
        }
    }
}
