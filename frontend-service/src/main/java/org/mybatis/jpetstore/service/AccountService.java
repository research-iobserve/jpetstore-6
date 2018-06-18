/**
 *    Copyright (C) 2010-2017 the original author or authors.
 *                  2017 iObserve Project (https://www.iobserve-devops.net)
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
package org.mybatis.jpetstore.service;

import org.mybatis.jpetstore.domain.Account;
import org.springframework.stereotype.Service;

/**
 * The Class AccountService.
 *
 * @author Eduardo Macarron
 * @author Reiner Jung -- adapted for distributed JPetStore
 */
@Service
public class AccountService extends AbstractService {

    private static final String ACCOUNT_SERVICE = "http://account" + AbstractService.getDomain()
            + ":8080/jpetstore-account/";
    private static final String REQUEST_USER = AccountService.ACCOUNT_SERVICE + "request-user";
    private static final String INSERT_ACCOUNT_REQUEST = AccountService.ACCOUNT_SERVICE + "insert-account";
    private static final String UPDATE_ACCOUNT_REQUEST = AccountService.ACCOUNT_SERVICE + "update-account";

    /**
     * Return the account for the given user name.
     *
     * @param username
     *            the user name
     * @return the matching account or null
     */
    public Account getAccount(final String username) {
        return this.getSingleValue(AccountService.REQUEST_USER + "?username=" + username, Account.class);
    }

    /**
     * Return the account for the given user name and password.
     *
     * @param username
     *            the user name
     * @param password
     *            the user's password
     * @return the matching account or null
     */
    public Account getAccount(final String username, final String password) {
        return this.getSingleValue(AccountService.REQUEST_USER + "?username=" + username + "&password=" + password,
                Account.class);
    }

    /**
     * Insert account.
     *
     * @param account
     *            the account
     */
    public void insertAccount(final Account account) {
        AbstractService.LOG.info("insert " + account.toString());
        this.postOperation(AccountService.INSERT_ACCOUNT_REQUEST, account);
    }

    /**
     * Update account.
     *
     * @param account
     *            the account
     */
    public void updateAccount(final Account account) {
        AbstractService.LOG.info("update " + account.toString());
        this.postOperation(AccountService.UPDATE_ACCOUNT_REQUEST, account);
    }

}
