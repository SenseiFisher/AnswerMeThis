package google;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonStreamParser;

import main.EnvironmentConstansts;

class GoogleAPISearch extends Google {
	// Final variables
	private final String API_KEY = EnvironmentConstansts.GOOGLE_API_KEY;
	private final String GOOGLE_API_URL = "https://www.googleapis.com/customsearch/v1?key=%s&cx=013036536707430787589:_pqjad5hr1a&q=%s&alt=json&lr=lang_iw";

//	public static Google getInstance() {
//		if (Instance == null) {
//			Instance = new GoogleAPISearch();
//		}
//		return Instance;
//	}

	/**
	 * Search given string on google
	 * 
	 * @param searchString
	 *            - The string you wish to search
	 * @throws IOException
	 */
	public void Search(String searchString) throws IOException {
		// Nullify the search results in case the search will fail
		_searchResults = null;
		_lastSearchString = searchString;
		String formattedSearchString = URLEncoder.encode(searchString.replace(" ", "+").replace("\"", "\\\""), EnvironmentConstansts.CHARSET);
		URL url = new URL(String.format(GOOGLE_API_URL, API_KEY, formattedSearchString));
		Reader reader = new InputStreamReader(url.openStream(), EnvironmentConstansts.CHARSET);
		JsonStreamParser jsonReader = new JsonStreamParser(reader);
		JsonArray foundItems = jsonReader.next().getAsJsonObject().getAsJsonArray("items");

		// If results were found
		if (foundItems != null) {
			_searchResults = foundItems.iterator();
		}
	}
}
