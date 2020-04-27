/***************************************************************************
 * Copyright (C) 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package org.mybatis.jpetstore.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

/**
 * @author Reiner Jung
 */
public abstract class AbstractService {

    protected static final Logger LOG = Logger.getLogger(CatalogService.class);

    /**
     * Get the jpetstore domain form JPETSTORE_DOMAIN environment variable.
     *
     * @return returns the variable value or an empty string
     */
    protected static final String getDomain() {
    	String domain = System.getenv("JPETSTORE_DOMAIN");
    	if (domain == null) {
    		return "";
    	} else {
    		return domain;
	}
    }

    /**
     * Request a single object from remote.
     *
     * @param url
     *            query url
     * @param clazz
     *            type of the object to be requested
     * @param <T> value type
     * @return returns the object or null on error
     */
    protected <T> T getSingleValue(final String url, final Class<T> clazz) {
        try {
            AbstractService.LOG.info("get from remote " + url);
            final NetHttpTransport httpTransport = new NetHttpTransport();
            final HttpRequest request = httpTransport.createRequestFactory().buildGetRequest(new GenericUrl(url));
            final HttpResponse response = request.execute();

            if (response.getStatusCode() == 200) {
                final ObjectMapper mapper = new ObjectMapper();
                final T object = mapper.readValue(response.getContent(), clazz);

                return object;
            } else {
                return null;
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Request a list of objects from remote.
     *
     * @param url
     *            query url
     * @param clazz
     *            type of the objects to be requested
     * @param <T> result value type
     * @return returns the list or null on error
     */
    protected <T> List<T> getMultipleValues(final String url, final Class<T> clazz) {
        try {
            AbstractService.LOG.info("get from remote " + url);
            final NetHttpTransport httpTransport = new NetHttpTransport();
            final HttpRequest request = httpTransport.createRequestFactory().buildGetRequest(new GenericUrl(url));
            final HttpResponse response = request.execute();

            if (response.getStatusCode() == 200) {
                final ObjectMapper mapper = new ObjectMapper();
                final List<T> list = mapper.readValue(response.getContent(),
                        mapper.getTypeFactory().constructCollectionType(List.class, clazz));

                return list;
            } else {
                return null;
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Post an object.
     *
     * @param url
     *            where
     * @param object
     *            the object
     * @param <T> object type to be processed
     *
     * @return result code
     */
    protected <T> int postOperation(final String url, final T object) {
        final StringWriter sw = new StringWriter();

        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(sw, object);

            final HttpClient httpClient = HttpClientBuilder.create().build();

            try {
                final HttpPost request = new HttpPost(url);
                final StringEntity params = new StringEntity(sw.toString());
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                final org.apache.http.HttpResponse response = httpClient.execute(request);

                return response.getStatusLine().getStatusCode();
            } catch (final Exception ex) {
                ex.printStackTrace();
                return 404;
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return 404;
        }
    }
}
