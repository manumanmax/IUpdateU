package IUpdateU.link;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import IUpdateU.tasks.CheckUpdateTask;

public class WebpageContentContainer {
	private String pageAddress;
	private String currentContentXML = "";
	private String currentContentText = "";
	private WebClient webClient = new WebClient();
	private DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM,
			new Locale("EN", "en"));
	private File saveFolder;
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	public WebpageContentContainer(String pageAddress, String saveFolderLocation)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		super();
		this.pageAddress = pageAddress;
		final HtmlPage page = webClient.getPage(pageAddress);
		currentContentXML = page.asXml();
		currentContentText = page.asText();
		saveFolder = new File(saveFolderLocation);
	}

	public void startFetchPage() {
		executor.scheduleAtFixedRate(new CheckUpdateTask(this, pageAddress), 0, 60, TimeUnit.SECONDS);
	}

	public void stopFetchPage() {
		executor.shutdownNow();
	}

	public void updateText(HtmlPage page) throws IOException {
		final String pagePlainText = page.asText();
		String dateAndTime = dateFormat.format(new Date());
		if (page.equals(currentContentText)) {
			System.out.println(dateAndTime + ": No need to update.");
		} else {
			System.err.println(dateAndTime + ": Update in the text.");
			if (saveFolder.exists()) {
				File backupFolder = new File(saveFolder.getPath() + "\\BackupText");
				if (backupFolder.exists()) {
					File backupFile = new File(backupFolder.getPath() + "\\" + dateFormat(dateAndTime) + ".txt");
					addTextContent(backupFile);
					currentContentText = pagePlainText;
				} else {
					createFolder(backupFolder);
					updateText(page);
				}
			} else {
				createFolder(saveFolder);
				updateText(page);
			}
		}
	}

	public void updateXML(HtmlPage page) throws IOException {
		final String pageXML = page.asXml();
		String dateAndTime = dateFormat.format(new Date());
		if (page.equals(currentContentXML)) {
			System.out.println(dateAndTime + ": No need to update.");
		} else {
			System.err.println(dateAndTime + ": Update in the XML.");
			if (saveFolder.exists()) {
				File backupFolder = new File(saveFolder.getPath() + "\\BackupXML");
				if (backupFolder.exists()) {
					File backupFile = new File(backupFolder.getPath() + "\\" + dateFormat(dateAndTime) + ".xml");
					addXMLContent(backupFile);
					currentContentXML = pageXML;
				} else {
					createFolder(backupFolder);
					updateText(page);
				}
			} else {
				createFolder(saveFolder);
				updateText(page);
			}
		}
	}

	private String dateFormat(String dateAndTime) {
		return dateAndTime.replace(' ', '_').replace(':', '_').replace(',', '_');
	}

	private void createFolder(File folder) {
		System.err.println("Creating the backup folder: " + folder.getPath());
		folder.mkdir();
	}

	private void addTextContent(File backupFile) {
		try {
			FileWriter writer = new FileWriter(backupFile.getPath());
			BufferedWriter buffer = new BufferedWriter(writer);
			buffer.write(currentContentText);
			writer.close();
			buffer.close();
		} catch (IOException e) {
			System.err.println("Can't write in the backup file " + backupFile.getName());
		}
	}

	private void addXMLContent(File backupFile) {
		try {
			FileWriter writer = new FileWriter(backupFile.getPath());
			BufferedWriter buffer = new BufferedWriter(writer);
			buffer.write(currentContentXML);
			writer.close();
			buffer.close();
		} catch (IOException e) {
			System.err.println("Can't write in the backup file " + backupFile.getName());
		}
	}
}
