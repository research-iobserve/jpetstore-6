/**
 *    Copyright (C) 2010-2017 the original author or authors.
 *                  2018 iObserve Project (https://www.iobserve-devops.net)
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
import org.mybatis.jpetstore.mapper.IAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class AccountService.
 *
 * @author Eduardo Macarron
 */
@Service
public class AccountService {

    @Autowired
    private IAccountMapper accountMapper;

    public Account getAccount(final String username) {
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(final String username, final String password) {
        return accountMapper.getAccountByUsernameAndPassword(username, password);
    }

    /**
     * Insert account.
     *
     * @param account
     *            the account
     */
    @Transactional
    public void insertAccount(final Account account) {
        accountMapper.insertAccount(account);
        accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    /**
     * Update account.
     *
     * @param account
     *            the account
     */
    @Transactional
    public void updateAccount(final Account account) {
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);

        if (account.getPassword() != null && account.getPassword().length() > 0) {
            accountMapper.updateSignon(account);
        }
    }

}
