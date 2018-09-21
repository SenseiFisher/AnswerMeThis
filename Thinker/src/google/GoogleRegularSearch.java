package google;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonStreamParser;

import main.EnvironmentConstansts;

public class GoogleRegularSearch extends Google {
	
	@Override
	public void Search(String searchString) throws IOException {
		String google = "http://www.google.com/search?q=";
		String charset = EnvironmentConstansts.CHARSET;
		String userAgent = "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6"; // Change this to your company's name and bot homepage!

		Elements links = Jsoup.connect(google + URLEncoder.encode(searchString, charset)).userAgent(userAgent).get().select(".g>.r>a");

		// Open a JSON array
		StringBuilder results = new StringBuilder("[");
		
		for (Element link : links) {
		    String title = link.text();
		    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
		    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

		    if (!url.startsWith("http")) {
		        continue; // Ads/news/etc.
		    }
		    
		    // Add backslash before every double quotes
		    title = title.replace("\"", "\\\"");
		    url = url.replace("\"", "\\\"");
		    
		    // Create a JSON
		    results.append("{ \"link\":\"").append(url).append("\", ")
		    		.append("\"title\":\"").append(title).append("\"},");
		}
		// Remove last ','
		results.deleteCharAt(results.length() - 1);
		
		// Close the JSON array
		results.append("]");
		JsonArray foundItems = new JsonStreamParser(results.toString()).next().getAsJsonArray();

		// If results were found
		if (foundItems != null) {
			_searchResults = foundItems.iterator();
		}
	}
}
