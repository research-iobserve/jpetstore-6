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
 * Provides the account update functionality. Input is a JSON serialization of a Account object.
 *
 * @author Reiner Jung
 */
@WebServlet("/update-account")
public class UpdateAccountServlet extends AbstractAccountServlet {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(UpdateAccountServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAccountServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        UpdateAccountServlet.LOG.error("update-account: GET not supported, requires POST");
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
            this.service.updateAccount(account);
        } else {
            UpdateAccountServlet.LOG.error("update-account: NO ACCOUNT to update");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
