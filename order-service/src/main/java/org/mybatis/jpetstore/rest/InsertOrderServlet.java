package org.mybatis.jpetstore.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mybatis.jpetstore.domain.Order;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AccountRestServlet
 */
@WebServlet("/insert-account")
public class InsertOrderServlet extends AbstractServlet {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(InsertOrderServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertOrderServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        InsertOrderServlet.LOG.error("insert-order: GET not supported, requires POST");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final Order order = mapper.readValue(request.getReader(), Order.class);

        if (order != null) {
        	LOG.error("order " + order.toString());
            this.orderService.insertOrder(order);
        } else {
            InsertOrderServlet.LOG.error("insert-order NO ORDER to insert");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
