package org.mybatis.jpetstore.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.mybatis.jpetstore.service.AccountService;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractServlet extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(AbstractServlet.class);

    protected transient AccountService accountService;

    /**
     *
     */
    private static final long serialVersionUID = -230552059269908269L;
    protected AutowireCapableBeanFactory ctx;

    @Override
    public void init() throws ServletException {
        super.init();
        // ctx = ((ApplicationContext) getServletContext().getAttribute("applicationContext"))
        // .getAutowireCapableBeanFactory();

        final WebApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(this.getServletContext());
        this.ctx = context.getAutowireCapableBeanFactory();
        this.ctx.autowireBean(this);

        // The following line does the magic
        this.ctx.autowireBean(this);

        this.accountService = (AccountService) context.getBean("AccountService");
        AbstractServlet.LOG.info("bean found " + this.accountService);

    }

}