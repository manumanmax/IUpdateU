package IUpdateU.tasks;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import IUpdateU.link.WebpageContentContainer;

public class CheckUpdateTask implements Runnable {
	WebpageContentContainer container;
	private WebClient webClient = new WebClient();
	final String pageAddress;

	public CheckUpdateTask(WebpageContentContainer container, String pageAddress) {
		super();
		this.container = container;
		this.pageAddress = pageAddress;
	}

	@Override
	public void run() {
		HtmlPage page = null;
		try {
			page = webClient.getPage(pageAddress);
		} catch (FailingHttpStatusCodeException | IOException e) {
			System.err.println("Can't load the page: " + pageAddress);
		}
		if (page != null) {
			try {
				container.updateText(page);
				container.updateXML(page);
			} catch (IOException e) {
				System.out.println("Can't update the container.");
			}
		}
	}

}
