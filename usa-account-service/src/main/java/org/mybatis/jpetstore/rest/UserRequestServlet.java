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
package org.mybatis.jpetstore.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mybatis.jpetstore.domain.Account;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AccountRestServlet
 */
@WebServlet("/request-user")
public class UserRequestServlet extends AbstractAccountServlet {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(UserRequestServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRequestServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String username = request.getParameter("username");
        if (username != null) {
            final String password = request.getParameter("password");

            if (password != null) {
                UserRequestServlet.LOG.info("login with " + username);
                this.sendResult(response, this.service.getAccount(username, password));
            } else {
                UserRequestServlet.LOG.info("user request " + username);
                this.sendResult(response, this.service.getAccount(username));
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private void sendResult(final HttpServletResponse response, final Account account) throws IOException {
        if (account == null) {
            UserRequestServlet.LOG.info("account not found");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), account);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

}
