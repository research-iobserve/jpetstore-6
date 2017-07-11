package org.mybatis.jpetstore.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mybatis.jpetstore.mapper.IAccountMapper;
import org.mybatis.jpetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * Servlet implementation class AccountRestServlet
 */
@WebServlet("/AccountRestServlet")
public class AccountRestServlet extends AbstractServlet {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(AccountRestServlet.class);

    @SpringBean
    private transient AccountService accountService;
    
    @Autowired
    private IAccountMapper accountMapper;

    private final AccountService ac = new AccountService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountRestServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        AccountRestServlet.LOG.info(("ac" + this.ac) != null ? " exists" : " missing");
        AccountRestServlet.LOG.info("use " + this.ac.hashCode());
        AccountRestServlet.LOG.info("access " + this.ac.getAccount("d"));

        response.getWriter().append("Served at xxx: ").append(request.getContextPath())
                .append(this.ac.getAccount("d") != null ? " d exists " : " d missing ");
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
