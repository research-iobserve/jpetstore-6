package org.mybatis.jpetstore.rest;

import javax.servlet.ServletException;

import org.mybatis.jpetstore.service.AccountService;

public abstract class AbstractAccountServlet extends AbstractServlet<AccountService> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -78529154022533581L;

	@Override
    public void init() throws ServletException {
        super.init("AccountService");
    }

}