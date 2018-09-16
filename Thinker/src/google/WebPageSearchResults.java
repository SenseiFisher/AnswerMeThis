package google;

import java.util.ArrayList;
import java.util.HashMap;

public class WebPageSearchResults {
	private HashMap<String, ArrayList<Integer>> results;
	
	public WebPageSearchResults(){
		results = new HashMap<String, ArrayList<Integer>>();
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
}
