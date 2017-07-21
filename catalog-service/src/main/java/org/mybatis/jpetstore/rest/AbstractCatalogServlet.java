package org.mybatis.jpetstore.rest;

import javax.servlet.ServletException;

import org.mybatis.jpetstore.service.CatalogService;

public abstract class AbstractCatalogServlet extends AbstractServlet<CatalogService> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7152202991760854415L;

	public void init() throws ServletException {
		super.init(CatalogService.class.getSimpleName());
	}
}
