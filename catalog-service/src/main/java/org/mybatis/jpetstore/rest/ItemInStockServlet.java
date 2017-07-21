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

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Reiner Jung
 *
 */
@WebServlet("/item-in-stock")
public class ItemInStockServlet extends AbstractCatalogServlet {

    /**
     *
     */
    private static final long serialVersionUID = 6512501817819164633L;
    private final static Logger LOG = Logger.getLogger(ItemInStockServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String itemId = request.getParameter("itemId");
        if (itemId != null) {
            this.sendResult(response, this.service.isItemInStock(itemId));
        } else {
            ItemInStockServlet.LOG.error("no item id specified");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        ItemInStockServlet.LOG.error("no post requests permitted for ItemInStockServlet");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void sendResult(final HttpServletResponse response, final boolean itemExists) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), itemExists);
    }
}
