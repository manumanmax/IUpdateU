package IUpdateU.tests;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FetchContentTests {

	private WebClient webClient;

	@Test
	public void test() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		webClient = new WebClient();
		final HtmlPage page = webClient.getPage("https://funforfox.blogspot.de/");

		Assert.assertEquals("Body and mind", page.getTitleText());

		final String pageAsXml = page.asXml();
		System.out.println(pageAsXml);
		Assert.assertTrue("Change in the body tag", pageAsXml.contains("<body class=\"loading variant-studio\">"));

		final String pageAsText = page.asText();
		Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
	}
}
