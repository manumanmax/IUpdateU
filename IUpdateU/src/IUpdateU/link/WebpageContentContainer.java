package IUpdateU.link;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebpageContentContainer {
	private String pageAddress;
	private String currentContentXLM = "";
	private String currentContentText = "";
	private WebClient webClient = new WebClient();
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	public WebpageContentContainer(String pageAddress)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super();
		this.pageAddress = pageAddress;
		final HtmlPage page = webClient.getPage(pageAddress);
		currentContentXLM = page.asXml();
		currentContentText = page.asText();
	}

	public void startFetchPage() {

	}

}
