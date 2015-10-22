package com.izeye.brokenlinkfinder.core.service;

import com.izeye.brokenlinkfinder.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by izeye on 15. 10. 22..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class BrokenLinkFinderTests {
	
	@Autowired
	BrokenLinkFinder brokenLinkFinder;
	
	@Test
	public void test() {
		String url = "http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/";
		brokenLinkFinder.find(url).stream()
				.filter(link -> !link.getUrl().contains("://localhost"))
				.forEach(System.out::println);
	}
	
}
