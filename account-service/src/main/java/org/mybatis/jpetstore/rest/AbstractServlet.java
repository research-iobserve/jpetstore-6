package org.mybatis.jpetstore.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -230552059269908269L;
	protected AutowireCapableBeanFactory ctx;

	@Override
	public void init() throws ServletException {
		super.init();
		//ctx = ((ApplicationContext) getServletContext().getAttribute("applicationContext"))
		//		.getAutowireCapableBeanFactory();
		
		WebApplicationContext context = WebApplicationContextUtils
	            .getWebApplicationContext(getServletContext());
	    ctx = context.getAutowireCapableBeanFactory();
	    ctx.autowireBean(this);
		
		// The following line does the magic
		ctx.autowireBean(this);

	}

}