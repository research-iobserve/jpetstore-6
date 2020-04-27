/**
 *    Copyright (C) 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
