package google;

public class WebPage {
	private String url;
	private String title;
	private String siteData;

	// Constructors
	public WebPage(String siteData){
		this.siteData = siteData;
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
		
	public WebPageSearchResults search(String... searchStrings){
		WebPageSearchResults results = new WebPageSearchResults();
		for (String searchString : searchStrings) {
			int currIndex = 0;
			for (int i = 0; i < siteData.length(); i++) {
				if (siteData.charAt(i) == searchString.charAt(currIndex)) {
					currIndex++;
				}
				else{
					currIndex = 0;
				}
				if (currIndex == searchString.length()) {
					results.addResult(searchString, i - searchString.length());
					currIndex = 0;
				}
			}
		}
		return results;
	}
	
	public boolean isWordCloseToPosition(String word, int position){
		final int lengthFromWord = 15;
		//TODO: check bounds
		int lowerBound = position - lengthFromWord;
		int upperBound = position + word.length() + lengthFromWord;
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
