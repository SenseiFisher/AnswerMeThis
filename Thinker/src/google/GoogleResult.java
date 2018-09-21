package google;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import main.EnvironmentConstansts;

public class GoogleResult {
	private String link;
	private String title;
	private String snippet; 

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getLink() {
		return link;
	}

	public String getTitle() {
		return title;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public WebPage enter() throws IOException{
        URL url = new URL(link);
        int pageSize = getFileSize(url);
        if(pageSize > 10_000_000){
        	throw new IOException("Can't Read large file");
        }
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
	
	private static int getFileSize(URL url) throws IOException {
	    URLConnection conn = null;
	    try {
	        conn = url.openConnection();
	        if(conn instanceof HttpURLConnection) {
	            ((HttpURLConnection)conn).setRequestMethod("HEAD");
	        }
	        conn.getInputStream();
	        return conn.getContentLength();
	    } finally {
	        if(conn instanceof HttpURLConnection) {
	            ((HttpURLConnection)conn).disconnect();
	        }
	    }
	}
	
	@Override
	public String toString() {
		return "Result[url:" + link + ",title:" + title + ",snippet:" + snippet + "]";
	}
}
