package com.izeye.brokenlinkfinder.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests for {@link BrokenLinkFinder}.
 *
 * @author Johnny Lim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BrokenLinkFinderTests {
	
	private static final Set<String> SPRING_BOOT_KNOWN_INVALID_URLS;
	
	static {
		Set<String> springBootKnownInvalidUrls = new HashSet<>();
		springBootKnownInvalidUrls.add("https://uaa.run.pivotal.io/userinfo");
		springBootKnownInvalidUrls.add("https://uaa.run.pivotal.io/check_token");
		springBootKnownInvalidUrls.add("http://acloudyspringtime.cfapps.io/");
		SPRING_BOOT_KNOWN_INVALID_URLS = Collections.unmodifiableSet(springBootKnownInvalidUrls);
	}
	
	@Autowired
	private BrokenLinkFinder brokenLinkFinder;
	
	@Test
	public void test() {
		String url = "http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/";
//		String url = "http://docs.spring.io/spring-restdocs/docs/current-SNAPSHOT/reference/html5/";
		this.brokenLinkFinder.find(url).stream()
				.filter(link -> !link.getUrl().contains("://localhost")
						&& !SPRING_BOOT_KNOWN_INVALID_URLS.contains(link.getUrl()))
				.forEach(System.out::println);
	}
	
}
