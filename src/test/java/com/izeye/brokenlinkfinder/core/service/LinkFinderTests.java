package com.izeye.brokenlinkfinder.core.service;

import com.izeye.brokenlinkfinder.Application;
import com.izeye.brokenlinkfinder.core.domain.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by izeye on 15. 10. 22..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class LinkFinderTests {

	@Autowired
	LinkFinder linkFinder;

	@Test
	public void test() {
		String url = "http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/";
		List<Link> links = linkFinder.find(url);
		links.forEach(System.out::println);
		System.out.println(links.size() + " links has been found.");
	}
	
}
