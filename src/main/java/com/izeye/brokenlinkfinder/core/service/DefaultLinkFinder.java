package com.izeye.brokenlinkfinder.core.service;

import com.izeye.brokenlinkfinder.core.domain.Link;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by izeye on 15. 10. 22..
 */
@Service
public class DefaultLinkFinder implements LinkFinder {
	
	private static final String PROTOCOL_DELIMITER = "://";
	private static final int PROTOCOL_DELIMITER_LENGTH = PROTOCOL_DELIMITER.length();
	
	@Override
	public List<Link> find(String url) {
		List<Link> links = new ArrayList<>();
		try {
			Document document = Jsoup.connect(url).get();
			Elements elements = document.select("a");
			for (Element element : elements) {
				String text = element.text();
				String href = element.attr("href");
				if (href.isEmpty() || href.startsWith("#")) {
					continue;
				}
				
				String linkUrl;
				if (href.startsWith("http")) { // Neither HTTP nor HTTPS
					linkUrl = href;
				}
				else {
					linkUrl = getLinkUrl(url, href);
				}
				links.add(new Link(text, href, linkUrl));
			}
			return links;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private String getLinkUrl(String url, String href) {
		String urlPrefix;
		if (href.startsWith("/")) { // Absolute path
			int fromIndex = url.indexOf(PROTOCOL_DELIMITER)
					+ PROTOCOL_DELIMITER_LENGTH;
			urlPrefix = url.substring(0, url.indexOf("/", fromIndex));
		}
		else { // Relative path
			urlPrefix = url.substring(0, url.lastIndexOf("/"));
		}
		return urlPrefix + href;
	}

}
