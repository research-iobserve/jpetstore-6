/**
 *   Copyright (C) 2010-2017 the original author or authors.
 *                 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.mybatis.jpetstore.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Abstract servlet.
 *
 * @param <T> type of a service
 *
 * @author Reiner Jung
 */
public abstract class AbstractServlet<T> extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(AbstractServlet.class);

	/** Service object. */
	protected transient T service;

	/**
	 *
	 */
	private static final long serialVersionUID = -230552059269908269L;
	protected AutowireCapableBeanFactory ctx;

	/**
	 * Initialization method.
	 *
	 * @param beanName name of the associated bean
	 *
	 * @throws ServletException on errors
	 */
	@SuppressWarnings("unchecked")
	public void init(final String beanName) throws ServletException {
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
