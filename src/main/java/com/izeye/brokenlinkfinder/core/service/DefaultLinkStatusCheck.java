package com.izeye.brokenlinkfinder.core.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by izeye on 15. 10. 22..
 */
@Service
public class DefaultLinkStatusCheck implements LinkStatusChecker {
	
	private final HttpClient httpClient = HttpClients.createDefault();
	
	@Override
	public int getStatusCode(String url) {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(request);
			return response.getStatusLine().getStatusCode();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
