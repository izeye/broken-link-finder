package com.izeye.brokenlinkfinder.core.service;

import com.izeye.brokenlinkfinder.Application;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by izeye on 15. 10. 22..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class LinkStatusCheckerTests {
	
	@Autowired
	LinkStatusChecker linkStatusChecker;

	@Test
	public void testGetStatusCodeOk() {
		String url = "https://www.google.co.kr/";
		assertThat(linkStatusChecker.getStatusCode(url), is(HttpStatus.SC_OK));
	}

	@Test
	public void testGetStatusCodeNotFound() {
		String url = "https://www.google.co.kr/1";
		assertThat(linkStatusChecker.getStatusCode(url), is(HttpStatus.SC_NOT_FOUND));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetStatusCodeWithInvalidUrl() {
		String url = "http://github.com/spring-projects/spring-boot/tree/master/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure}/jersey/JerseyProperties.java";
		linkStatusChecker.getStatusCode(url);
	}
	
}
