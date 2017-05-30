package com.izeye.brokenlinkfinder.core.service;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Tests for {@link LinkStatusChecker}.
 *
 * @author Johnny Lim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LinkStatusCheckerTests {
	
	@Autowired
	private LinkStatusChecker linkStatusChecker;

	@Test
	public void testGetStatusCodeOk() {
		String url = "https://www.google.co.kr/";
		assertThat(this.linkStatusChecker.getStatusCode(url), is(HttpStatus.SC_OK));
	}

	@Test
	public void testGetStatusCodeNotFound() {
		String url = "https://www.google.co.kr/1";
		assertThat(this.linkStatusChecker.getStatusCode(url), is(HttpStatus.SC_NOT_FOUND));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetStatusCodeWithInvalidUrl() {
		String url = "http://github.com/spring-projects/spring-boot/tree/master/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure}/jersey/JerseyProperties.java";
		this.linkStatusChecker.getStatusCode(url);
	}
	
}
