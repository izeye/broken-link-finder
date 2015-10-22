package com.izeye.brokenlinkfinder.core.service;

import com.izeye.brokenlinkfinder.core.domain.Link;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by izeye on 15. 10. 22..
 */
@Service
public class DefaultBrokenLinkFinder implements BrokenLinkFinder {
	
	@Autowired
	private LinkFinder linkFinder;
	
	@Autowired
	private LinkStatusChecker linkStatusChecker;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<Link> find(String url) {
		List<Link> brokenLinks = new ArrayList<>();
		
		List<Thread> threads = new ArrayList<>();
		List<Link> links = linkFinder.find(url);
		for (Link link : links) {
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						int statusCode = linkStatusChecker.getStatusCode(link.getUrl());
						link.setStatusCode(statusCode);
					} catch (Throwable ex) {
						log.error("Unexpected exception.", ex);
					}
				}
			};
			threads.add(thread);
			thread.start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException ex) {
				log.error("Unexpected exception.", ex);
			}
		}
		for (Link link : links) {
			Integer statusCode = link.getStatusCode();
			if (statusCode == null || statusCode != HttpStatus.SC_OK) {
				brokenLinks.add(link);
			}
		}
		return brokenLinks;
	}
	
}
