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

import java.io.IOException;

import org.apache.log4j.Logger;
import org.mybatis.jpetstore.domain.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * The Class AccountService.
 *
 * @author Eduardo Macarron
 */
@Service
public class AccountService {

    private static final String ACCOUNT_SERVICE = "http://172.17.0.2:8080/jpetstore-account/";
    private static final String REQUEST_USER = AccountService.ACCOUNT_SERVICE + "request-user";
    private static final String INSERT_ACCOUNT_REQUEST = AccountService.ACCOUNT_SERVICE + "insert-account";
    private static final String UPDATE_ACCOUNT_REQUEST = AccountService.ACCOUNT_SERVICE + "update-account";

    private final static Logger LOG = Logger.getLogger(AccountService.class);

    /**
     * Return the account for the given user name.
     *
     * @param username
     *            the user name
     * @return the matching account or null
     */
    public Account getAccount(final String username) {
        return this.getAccountFromRemote(AccountService.REQUEST_USER + "?username=" + username);
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
        return this
                .getAccountFromRemote(AccountService.REQUEST_USER + "?username=" + username + "&password=" + password);
    }

    private Account getAccountFromRemote(final String url) {
        try {
            AccountService.LOG.info("get from remote " + url);
            final NetHttpTransport httpTransport = new NetHttpTransport();
            final HttpRequest request = httpTransport.createRequestFactory().buildGetRequest(new GenericUrl(url));
            final HttpResponse response = request.execute();

            if (response.getStatusCode() == 200) {
                final ObjectMapper mapper = new ObjectMapper();
                final Account account = mapper.readValue(response.getContent(), Account.class);

                return account;
            } else {
                return null;
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Insert account.
     *
     * @param account
     *            the account
     */
    @Transactional
    public void insertAccount(final Account account) {
        AccountService.LOG.info("insert " + account.toString());
        this.postOperation(AccountService.INSERT_ACCOUNT_REQUEST, account);
    }

    /**
     * Update account.
     *
     * @param account
     *            the account
     */
    @Transactional
    public void updateAccount(final Account account) {
        AccountService.LOG.info("update " + account.toString());
        this.postOperation(AccountService.UPDATE_ACCOUNT_REQUEST, account);
    }

    private int postOperation(final String url, final Object object) {
        final NetHttpTransport httpTransport = new NetHttpTransport();

        final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        AccountService.LOG.info("Object is " + (object != null ? "yes" : "no"));
        AccountService.LOG.info("url " + url);
        AccountService.LOG.info("object " + object.toString());

        final JsonHttpContent content = new JsonHttpContent(jsonFactory, object);

        try {
            final HttpRequest request = httpTransport.createRequestFactory().buildPostRequest(new GenericUrl(url),
                    content);
            final HttpResponse response = request.execute();
            AccountService.LOG.info("object " + response.getStatusCode() + " " + response.getStatusMessage());
            return response.getStatusCode();
        } catch (final IOException e) {
            e.printStackTrace();
            return 404;
        }
    }

}
