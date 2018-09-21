package google;

import java.io.IOException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public abstract class Google {
	
	private static Google _instance;
	
	// Custom variables
	protected String _lastSearchString;
	protected Iterator<JsonElement> _searchResults;
	
	public static Google getInstance(){
		if(_instance == null){
			_instance = new GoogleRegularSearch();
		}
		
		return _instance;
	}
	
	public void setSearchMethod(SearchMethodEnums method){
		switch(method){
		case GOOGLE_API:
			_instance = new GoogleAPISearch();
			break;
		case REGULAR:
			_instance = new GoogleRegularSearch();
			break;
		}
	}
	
	public abstract void Search(String searchString) throws IOException;
		
	
	public GoogleResult getNextResult() throws NoResultException {
		if (_searchResults == null || !_searchResults.hasNext()) {
			throw new NoResultException();
		}

		return new Gson().fromJson(_searchResults.next(), GoogleResult.class);
	}

	public boolean hasNextResult() {
		if(_searchResults == null){
			return false;
		}
		
		return _searchResults.hasNext();
	}

	public String getLastSearchString() {
		return _lastSearchString;
	}
	
	public enum SearchMethodEnums{
		GOOGLE_API,
		REGULAR
	}
}
