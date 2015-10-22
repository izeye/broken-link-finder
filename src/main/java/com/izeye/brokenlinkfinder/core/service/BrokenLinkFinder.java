package com.izeye.brokenlinkfinder.core.service;

import com.izeye.brokenlinkfinder.core.domain.Link;

import java.util.List;

/**
 * Created by izeye on 15. 10. 22..
 */
public interface BrokenLinkFinder {
	
	List<Link> find(String url);
	
}
