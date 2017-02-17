package IUpdateU.link;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebpageContentFetcher {
	private String pageAddress;
	private String currentContentXLM = "";
	private String currentContentText = "";
	private WebClient webClient = new WebClient();

	public WebpageContentFetcher(String pageAddress)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super();
		this.pageAddress = pageAddress;
		final HtmlPage page = webClient.getPage(pageAddress);
		currentContentXLM = page.asXml();
		currentContentText = page.asText();
	}

}
