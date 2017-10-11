package com.izeye.brokenlinkfinder.core.service;

import com.izeye.brokenlinkfinder.core.domain.Link;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Default {@link BrokenLinkFinder}.
 *
 * @author Johnny Lim
 */
@Service
@Slf4j
public class DefaultBrokenLinkFinder implements BrokenLinkFinder {
	
	@Autowired
	private LinkFinder linkFinder;
	
	@Autowired
	private LinkStatusChecker linkStatusChecker;

	private final ExecutorService executor = Executors.newFixedThreadPool(100);

	@Override
	public List<Link> find(String url) {
		List<Future<?>> futures = new ArrayList<>();
		List<Link> links = this.linkFinder.find(url);
		log.info("Total links: {}", links.size());

		for (Link link : links) {
			Future<?> future = this.executor.submit(() -> {
				try {
					int statusCode = this.linkStatusChecker.getStatusCode(link.getUrl());
					link.setStatusCode(statusCode);
				}
				catch (Throwable ex) {
					log.error("Unexpected exception.", ex);
				}
			});
			futures.add(future);
		}

		for (Future<?> future : futures) {
			try {
				future.get();
			}
			catch (InterruptedException ex) {
				log.error("Unexpected exception.", ex);
			}
			catch (ExecutionException ex) {
				log.error("Unexpected exception.", ex);
			}
		}

		List<Link> brokenLinks = new ArrayList<>();
		for (Link link : links) {
			Integer statusCode = link.getStatusCode();
			if (statusCode == null || statusCode != HttpStatus.SC_OK) {
				brokenLinks.add(link);
			}
		}
		return brokenLinks;
	}
	
}
