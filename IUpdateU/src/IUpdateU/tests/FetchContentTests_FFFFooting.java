package IUpdateU.tests;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import IUpdateU.link.WebpageContentContainer;

public class FetchContentTests_FFFFooting {

	private WebClient webClient;
	WebpageContentContainer container;

	@Test
	public void reachPageAndContentTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		webClient = new WebClient();
		final HtmlPage page = webClient.getPage("https://funforfox.blogspot.de/");

		Assert.assertEquals("Body and mind", page.getTitleText());

		final String pageAsXml = page.asXml();
		Assert.assertTrue("Change in the body tag", pageAsXml.contains("<body class=\"loading variant-studio\">"));

		final String pageAsText = page.asText();
		Assert.assertTrue(pageAsText.contains("Footing for a fox"));
	}

	@Before
	public void init() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		container = new WebpageContentContainer("https://funforfox.blogspot.de/", "C:\\Users\\Emmanuel\\git\\Backups");
		container.startFetchPage();
		container.stopFetchPage();
	}

	@Test
	public void schedulingTaskTest() {

	}
}
