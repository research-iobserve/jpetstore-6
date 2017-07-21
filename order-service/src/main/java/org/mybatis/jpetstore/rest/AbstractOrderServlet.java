package org.mybatis.jpetstore.rest;

import javax.servlet.ServletException;

import org.mybatis.jpetstore.service.OrderService;

public abstract class AbstractOrderServlet extends AbstractServlet<OrderService> {

	/**
     *
     */
    private static final long serialVersionUID = -230552059269908269L;

    @Override
    public void init() throws ServletException {
        super.init("OrderService");
    }

}