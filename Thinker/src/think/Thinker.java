package think;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.helper.StringUtil;

import google.Google;
import google.NoResultException;
import google.WebPage;
import google.WebPageSearchResults;
import main.EnvironmentConstansts;

public class Thinker {
	
	private static Thinker _instance;
	
	private Thinker(){
	}
	
	public static Thinker getInstance(){
		if(_instance == null){
			_instance = new Thinker();
		}
		
		return _instance;
	}
	
	public String AnswerMeThis(String Question, String[] Answers) throws IOException, NoResultException{
		String processedQuestion = proccessQuestion(Question);
		Google.getInstance().Search(processedQuestion);
		int numberOfCheckedWebsites = 0;
		String correctAnswer = null;
		
		// While there is google results and an answer was not found
		while(Google.getInstance().hasNextResult() && 
				numberOfCheckedWebsites < 3 &&
				correctAnswer == null){
			WebPage webSite = null;
			
			try{
				webSite = Google.getInstance().getNextResult().enter();								
			}catch(IOException e){
				// If unable to enter the website then just continue to the next one
				continue;
			}
			numberOfCheckedWebsites++;
			
			correctAnswer = getAnswerFromWebsite(processedQuestion, Answers, webSite);
		}
		return correctAnswer;
	}
	
	private String getAnswerFromWebsite(String question, String[] answers, WebPage website){	
		String correctAnswer = null;
		
		WebPageSearchResults results = website.search(false, answers);
		
		ArrayList<String> zeroOccurrences = new ArrayList<String>();
		ArrayList<String> moreOccurrences = new ArrayList<String>();
		for (String searchWord : answers) {
			if(results.getOccurrences(searchWord).size() == 0){
				zeroOccurrences.add(searchWord);					
			}else{
				moreOccurrences.add(searchWord);
			}				
		}
		
		SEARCH_FOR searchMethod = getSearchMethod(question);
		
		// If we search for no occurrences and there is only one value without occurrences,
		// Then that our answer.
		if (searchMethod == SEARCH_FOR.NO_OCCURRENCE && zeroOccurrences.size() == 1) {
			correctAnswer = zeroOccurrences.get(0);
		} else if (searchMethod == SEARCH_FOR.MOST_COMPATIBLE) {
			String foundAnswer = chooseAnswerCorrect(question, website, results);
			if(foundAnswer != null){
				correctAnswer = foundAnswer;
			}
		}else if(searchMethod == SEARCH_FOR.EARLIEST){
			correctAnswer =	chooseEarliest(website, results);
		}
		
		return correctAnswer;
	}
	
	private SEARCH_FOR getSearchMethod(String Question){
		// Set most compatible as default method
		SEARCH_FOR searchMethod = SEARCH_FOR.MOST_COMPATIBLE;
		
		if(containsAny(Question, EnvironmentConstansts.NEGETIVE_WORDS)){
			searchMethod = SEARCH_FOR.NO_OCCURRENCE;
		}
		
		if(containsAny(Question, EnvironmentConstansts.EARLY_WORDS)){
			searchMethod = SEARCH_FOR.EARLIEST;
		}
		
		if(containsAny(Question, EnvironmentConstansts.LATE_WORDS)){
			searchMethod = SEARCH_FOR.LATEST;
		}
		
		return searchMethod;
	}
	
	private String chooseEarliest(WebPage webSite, WebPageSearchResults results) {
		String correctAnswer = null;
		
		int minNumber = Integer.MAX_VALUE;
		for (String searchedWord : results.getSearchedWords()) {
			for (Integer occurrence : results.getOccurrences(searchedWord)) {
				int amountOfChars = 20;
				int currNumber = 0;
				for (String closeWord : webSite.getWordsBetween(occurrence - amountOfChars, occurrence + searchedWord.length() + amountOfChars)) {
					int wordLength = closeWord.length();
					if(wordLength <= 4 && StringUtil.isNumeric(closeWord)){
						// Append a word amount of zeros.
						// As the number grows he has more value, year vs month (2018 vs 5).
						// If we just sum up the numbers months can be count as years.
						currNumber += Integer.parseInt(closeWord)*(Math.pow(wordLength,10));
					}
				}
				if(currNumber < minNumber){
					minNumber = currNumber;
					correctAnswer = searchedWord;
				}
			}
		}
		
		return correctAnswer;
	}
	
	private String chooseAnswerCorrect(String questionToSearch, WebPage webSite, WebPageSearchResults results) {
		String correctAnswer = null;
		int maxPoints = 0;
		for (String word : results.getSearchedWords()) {
			// Give points for number of occurrences
			int wordPoints = results.getOccurrences(word).size();
			for (Integer occurrence : results.getOccurrences(word)) {
				
				// Check search result
				if(!isValidSearchResult(occurrence, word, webSite)){
					continue;
				}
				
				// Go over every word of the question
				for (String wordInQuestion : questionToSearch.split(" ")) {
					// If the a question word is close the the answer word
					if(webSite.isWordCloseToPosition(wordInQuestion, occurrence)){
						// Add the length of the question word.
						// Longer word equals more points,
						// less likelihood the characters combination of the word will be there. 
						wordPoints += fib(wordInQuestion.length());
					}						
				}
			}
			if(wordPoints >= maxPoints){
				maxPoints = wordPoints;
				correctAnswer = word;
			}
		}
		return correctAnswer;
	}
	
	private boolean isValidSearchResult(int position, String searchedWord, WebPage page){
		String fullWord = page.getWordsBetween(position, position)[0];
		
		// If the word is a number than we don't want any numbers chained to the number
		if(StringUtil.isNumeric(searchedWord)){
			for(int i=0;i<fullWord.length();i++){
				if(StringUtil.isNumeric(fullWord.charAt(i) + "")){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private String proccessQuestion(String question){
		StringBuilder newQuestion = new StringBuilder(); 
		String[] wordsOfQuestion = question.split(" ");
		
		// Get words in quotes
		boolean isInsideQuotes = false;
		for (int i = 0; i < wordsOfQuestion.length; i++) {
			String currWord = wordsOfQuestion[i];
			if(isInsideQuotes){
				newQuestion.append(currWord).append(" ");
			
			}else if(currWord.startsWith("\"")){
				isInsideQuotes = true;
				// Append the last word before the start of the quotes
				// Usually that word says what's the quotes represent
				// (מה כתוב בספר "בננה בננה")
				if(i > 0){
					newQuestion.append(wordsOfQuestion[i - 1]).append(" ");
				}
				newQuestion.append(currWord).append(" ");
			} 
			if(currWord.endsWith("\"")){
				isInsideQuotes = false;
			}
		}
		
		// If there are no words in quotes
		if(newQuestion.length() == 0){			
			// Remove insignificant words
			for (String wordOfQuestion : wordsOfQuestion) {
				boolean isImportant = true;
				for (String insignificantWord : EnvironmentConstansts.INSIGNIFICANT_WORDS) {
					if (insignificantWord.equals(wordOfQuestion)) {
						isImportant = false;
						break;
					}
				}

				if (isImportant) {
					newQuestion.append(wordOfQuestion).append(" ");
				}
			}
		}
		
		// Remove last space
		newQuestion.deleteCharAt(newQuestion.length() - 1);
		return newQuestion.toString();
	}

	/**
	 * Fibonacci function 
	 * @param index
	 * @return The fibonacci value of the given index 
	 */
	private long fib(int index) {
		if (index <= 1) {
			return index;
		} else {
			return fib(index - 1) + fib(index - 2);
		}
	}
	
	private boolean containsAny(String string, String[] words){
		boolean isContained = false;
		
		for (String word : words) {
			if(string.contains(" " + word + " ")){
				isContained = true;
				break;
			}
		}
		return isContained;
	}
	
	private enum SEARCH_FOR{
		NO_OCCURRENCE,
		MOST_COMPATIBLE,
		EARLIEST,
		LATEST
	}
}
