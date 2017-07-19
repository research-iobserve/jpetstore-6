package org.mybatis.jpetstore.rest;

import java.io.IOException;
import java.util.Collection;

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
public class AccountGetUserRestServlet extends AbstractServlet {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(AccountGetUserRestServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountGetUserRestServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        AccountGetUserRestServlet.LOG.info("request-user");
        final String username = request.getParameter("username");
        if (username != null) {
            AccountGetUserRestServlet.LOG.info("username is " + username);
            final String password = request.getParameter("password");

            if (password != null) {
                this.sendResult(response, this.accountService.getAccount(username, password));
            } else {
                AccountGetUserRestServlet.LOG.info("no password for " + username);
                this.sendResult(response, this.accountService.getAccount(username));
            }

        } else {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), null);

            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private void sendResult(final HttpServletResponse response, final Account account) throws IOException {
        if (account == null) {
            AccountGetUserRestServlet.LOG.info("account is empty");
            final Collection<Account> accounts = this.accountService.getAllAccounts();
            AccountGetUserRestServlet.LOG.info("found accounts " + accounts.size());
            for (final Account ac : accounts) {
                AccountGetUserRestServlet.LOG.info("account is " + ac.getUsername());
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            AccountGetUserRestServlet.LOG.info("account is " + account.getUsername());
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
        // TODO Auto-generated method stub
        this.doGet(request, response);
    }

}
