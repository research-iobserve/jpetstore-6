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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.mapper.IAccountMapper;

/**
 * @author Eduardo Macarron
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private IAccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void shouldCallTheMapperToInsertAnAccount() {
        // given
        final Account account = new Account();

        // when
        accountService.insertAccount(account);

        // then
        Mockito.verify(accountMapper).insertAccount(ArgumentMatchers.eq(account));
        Mockito.verify(accountMapper).insertProfile(ArgumentMatchers.eq(account));
        Mockito.verify(accountMapper).insertSignon(ArgumentMatchers.eq(account));
    }

}
