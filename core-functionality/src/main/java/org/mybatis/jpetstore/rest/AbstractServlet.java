package org.mybatis.jpetstore.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractServlet<T> extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(AbstractServlet.class);

    protected transient T service;

    /**
     *
     */
    private static final long serialVersionUID = -230552059269908269L;
    protected AutowireCapableBeanFactory ctx;

    @SuppressWarnings("unchecked")
	public void init(String beanName) throws ServletException {
        super.init();

        final WebApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(this.getServletContext());
        this.ctx = context.getAutowireCapableBeanFactory();
        this.ctx.autowireBean(this);

        // The following line does the magic
        this.ctx.autowireBean(this);

        this.service = (T) context.getBean(beanName);
        AbstractServlet.LOG.info("bean found " + this.service);

    }

}