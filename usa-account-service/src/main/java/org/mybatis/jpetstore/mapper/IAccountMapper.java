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
package org.mybatis.jpetstore.mapper;

import java.util.Collection;

import org.mybatis.jpetstore.domain.Account;

/**
 * The Interface AccountMapper.
 *
 * @author Eduardo Macarron
 */
public interface IAccountMapper {

    /**
     * Get the account by user name.
     *
     * @param username
     *            the user's name
     * @return the account or null if no account exists
     */
    Account getAccountByUsername(String username);

    Collection<Account> getAllAccounts();

    /**
     * Get the account for a given user name and matching password.
     *
     * @param username
     *            user's name
     * @param password
     *            user's password
     * @return the user's account or null if user does not exist or the password does not match
     */
    Account getAccountByUsernameAndPassword(String username, String password);

    /**
     * Add an account to the database.
     *
     * @param account
     *            the accont to be added
     */
    void insertAccount(Account account);

    /**
     * Insert new preferences for an account.
     *
     * @param account
     *            the account
     */
    void insertProfile(Account account);

    /**
     * Insert sign on/authentication values for an account.
     *
     * @param account
     *            the account
     */
    void insertSignon(Account account);

    /**
     * Update an existing account.
     *
     * @param account
     *            the account to be updated
     */
    void updateAccount(Account account);

    /**
     * Update preferences for an account.
     *
     * @param account
     *            the account
     */
    void updateProfile(Account account);

    /**
     * Update sign on/authentication values for an existing account.
     *
     * @param account
     *            the account
     */
    void updateSignon(Account account);

}
