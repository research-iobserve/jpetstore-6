package org.mybatis.jpetstore.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

public abstract class AbstractService {

	protected final static Logger LOG = Logger.getLogger(CatalogService.class);
	
	/**
	 * Request a single object from remote.
	 * 
	 * @param url query url
	 * @param clazz type of the object to be requested
	 * @return returns the object or null on error 
	 */
	protected <T> T getSingleValue(final String url, final Class<T> clazz) {
		try {
			LOG.info("get from remote " + url);
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
	 * @param url query url
	 * @param clazz type of the objects to be requested
	 * @return returns the list or null on error 
	 */
	protected <T> List<T> getMultipleValues(final String url, final Class<T> clazz) {
		try {
			LOG.info("get from remote " + url);
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
     * @param url where
     * @param object the object
     * @return result code
     */
	protected <T> int postOperation(final String url, final T object) {      
        StringWriter sw = new StringWriter();
        
        final ObjectMapper mapper = new ObjectMapper();
        try {
			mapper.writeValue(sw, object);
						
			HttpClient httpClient = HttpClientBuilder.create().build(); 

	        try {
	            HttpPost request = new HttpPost(url);
	            StringEntity params = new StringEntity(sw.toString());
	            request.addHeader("content-type", "application/json");
	            request.setEntity(params);
	            org.apache.http.HttpResponse response = httpClient.execute(request);

	            return response.getStatusLine().getStatusCode();
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        	return 404;
	        }
		} catch (IOException e) {
			e.printStackTrace();
			return 404;
		}
    }
}
