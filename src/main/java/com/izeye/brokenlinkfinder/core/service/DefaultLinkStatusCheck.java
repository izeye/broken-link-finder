package com.izeye.brokenlinkfinder.core.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Default {@link LinkStatusChecker}.
 *
 * @author Johnny Lim
 */
@Service
public class DefaultLinkStatusCheck implements LinkStatusChecker {
	
	@Override
	public int getStatusCode(String url) {
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			return response.getStatusLine().getStatusCode();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
