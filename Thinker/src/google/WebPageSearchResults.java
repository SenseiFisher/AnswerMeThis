package google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class WebPageSearchResults {
	private HashMap<String, ArrayList<Integer>> results;
	
	public WebPageSearchResults(){
		results = new HashMap<String, ArrayList<Integer>>();
	}
	
	public void mergeSearchResults(WebPageSearchResults results){
		for (String word : results.getSearchedWords()) {
			for (Integer position : results.getOccurrences(word)) {
				this.addResult(word, position);				
			}
		}
	}
	
	public void addResult(String searchString, Integer position){
		ArrayList<Integer> result = results.getOrDefault(searchString, new ArrayList<Integer>());
		result.add(position);
		results.putIfAbsent(searchString, result);
	}
	
	public ArrayList<Integer> getOccurrences(String searchString){
		ArrayList<Integer> stringOccurrences = results.get(searchString);
		if(stringOccurrences == null){
			stringOccurrences = new ArrayList<Integer>();
		}
		return stringOccurrences;
	}
	
	public Set<String> getSearchedWords(){
		return results.keySet();
	}
}
