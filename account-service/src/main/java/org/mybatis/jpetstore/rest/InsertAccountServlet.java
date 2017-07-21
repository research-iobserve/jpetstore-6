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
@WebServlet("/insert-account")
public class InsertAccountServlet extends AbstractAccountServlet {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(InsertAccountServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertAccountServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        InsertAccountServlet.LOG.error("insert-account: GET not supported, requires POST");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final Account account = mapper.readValue(request.getReader(), Account.class);

        if (account != null) {
        	LOG.error("account " + account.toString());
            this.service.insertAccount(account);
        } else {
            InsertAccountServlet.LOG.error("insert-account NO ACCOUNT to insert");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
