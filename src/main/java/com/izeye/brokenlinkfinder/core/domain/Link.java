package com.izeye.brokenlinkfinder.core.domain;

import lombok.Data;

/**
 * Created by izeye on 15. 10. 22..
 */
@Data
public class Link {
	
	private final String text;
	private final String href;
	private final String url;
	
	private Integer statusCode;
	
	public Link(String text, String href, String url) {
		this.text = text;
		this.href = href;
		this.url = url;
	}
	
}
