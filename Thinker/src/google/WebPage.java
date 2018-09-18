package google;

import org.jsoup.Jsoup;

public class WebPage {
	private String url;
	private String title;
	private String siteData;

	// Constructors
	public WebPage(String siteData){
		this.siteData = Jsoup.parse(siteData).text();	
	}
	public WebPage(String url, String title, String siteData){
		this(siteData);
		this.url = url;
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSiteData() {
		return siteData;
	}
	public void setSiteData(String siteData) {
		this.siteData = siteData;
	}
		
	public WebPageSearchResults search(boolean searchForWords, String... searchStrings){
		WebPageSearchResults results = new WebPageSearchResults();
		for (String originalSearchString : searchStrings) { 
			String searchString = originalSearchString;
			if(searchForWords){
				searchString = " " + originalSearchString + " ";
			}
			
			int currIndex = 0;
			for (int i = 0; i < siteData.length(); i++) {
				if (siteData.charAt(i) == searchString.charAt(currIndex)) {
					currIndex++;
				}
				else{
					currIndex = 0;
				}
				if (currIndex == searchString.length()) {
					results.addResult(originalSearchString, i - originalSearchString.length());
					currIndex = 0;
				}
			}
		}
		return results;
	}
	
	public boolean isWordCloseToPosition(String word, int position){
		// Define what 'close' means
		final int lengthFromWord = 25;
		
		// Get bounds to search between them
		int lowerBound = position - lengthFromWord;
		int upperBound = position + word.length() + lengthFromWord;
		
		// Check bounds
		if(lowerBound < 0){
			lowerBound = 0;
		}
		if(upperBound >= siteData.length()){
			upperBound = siteData.length() - 1;
		}
		
		return siteData.substring(lowerBound, upperBound).contains(word);
	}
	
	public String getWordAtPosition(int position){
		int currPosition = position;
		char currChar = siteData.charAt(position);
		StringBuilder word = new StringBuilder(currChar);
		while(!(currChar == ' ' || currChar == '\n')){
			currPosition++;
			currChar = siteData.charAt(currPosition);
			word.append(currChar);
		}
		
		return word.toString();
	}
}
