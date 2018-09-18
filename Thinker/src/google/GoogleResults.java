package google;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import main.EnvironmentConstansts;

public class GoogleResults {
	private String link;
	private String title;
	private String snippet; 

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getFormattedUrl() {
		return link;
	}

	public String getTitle() {
		return title;
	}

	public void setFormattedUrl(String formattedUrl) {
		this.link = formattedUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public WebPage enter() throws IOException{
        URL url = new URL(link);
		Reader reader = new InputStreamReader(url.openStream(), EnvironmentConstansts.CHARSET);
		String siteData = readerToString(reader);
		return new WebPage(link, title, siteData);
	}
	
	public String readerToString(Reader reader) throws IOException{
		//use string builder to avoid unnecessary string creation.
		StringBuilder builder = new StringBuilder();
		int charsRead = -1;
		char[] chars = new char[100];
		do{
		    charsRead = reader.read(chars,0,chars.length);

		    //if we have valid chars, append them to end of string.
		    if(charsRead>0)
		        builder.append(chars,0,charsRead);
		}while(charsRead>0);
		return builder.toString();
	}
	
	@Override
	public String toString() {
		return "Result[url:" + link + ",title:" + title + ",snippet:" + snippet + "]";
	}
}
