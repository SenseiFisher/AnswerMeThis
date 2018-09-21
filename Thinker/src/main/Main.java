package main;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.helper.StringUtil;

import google.Google;
import google.NoResultException;
import google.WebPage;
import google.WebPageSearchResults;

public class Main {
	
	public static void main(String[] args) {
		try {
			String searchInGoogle = "îé äéä øîèë\"ì öä\"ì åëáù àú éøåùìéí áîìçîú ùùú äéîéí";
			String[] searchInWebPage = {"îðçí áâéï", "îùä ãééï", "éöç÷ øáéï"};
			
			String answer = AnswerMeThis(searchInGoogle, searchInWebPage);
			System.out.println(answer);
		} catch (IOException | NoResultException e) {
			e.printStackTrace();
		}
	}
	
	public static String AnswerMeThis(String Question, String[] Answers) throws IOException, NoResultException{
		String questionToSearch = proccessQuestion(Question);
		Google.getInstance().Search(questionToSearch);
		int numberOfCheckedWebsites = 0;
		String correctAnswer = null;
		
		// While there is google results and an answer was not found
		while(Google.getInstance().hasNextResult() && 
				numberOfCheckedWebsites < 4 &&
				correctAnswer == null){
			WebPage webSite = null;
			
			try{
				webSite = Google.getInstance().getNextResult().enter();								
			}catch(IOException e){
				// If unable to enter the website then just continue to the next one
				continue;
			}
			numberOfCheckedWebsites++;			
			WebPageSearchResults results = webSite.search(false, Answers);
			
			
			ArrayList<String> zeroOccurrences = new ArrayList<String>();
			ArrayList<String> moreOccurrences = new ArrayList<String>();
			for (String searchWord : Answers) {
				if(results.getOccurrences(searchWord).size() == 0){
					zeroOccurrences.add(searchWord);					
				}else{
					moreOccurrences.add(searchWord);
				}				
			}
			
			// Set most compatible as default method
			SEARCH_METHOD searchMethod = SEARCH_METHOD.MOST_COMPATIBLE;

			String negativeWord = " ìà ";
			if(Question.contains(negativeWord)){
				searchMethod = SEARCH_METHOD.NO_OCCURRENCE;
			}
			
			if(Question.contains(" îå÷ãí ")){
				searchMethod = SEARCH_METHOD.EARLIEST;
			}
			if(Question.contains(" îàåçø ")){
				searchMethod = SEARCH_METHOD.LATEST;
			}
			
			// If we search for no occurrences and there is only one value without occurrences,
			// Then that our answer.
			if (searchMethod == SEARCH_METHOD.NO_OCCURRENCE && zeroOccurrences.size() == 1) {
				correctAnswer = zeroOccurrences.get(0);
			} else if (searchMethod == SEARCH_METHOD.MOST_COMPATIBLE) {
				String foundAnswer = chooseAnswerCorrect(questionToSearch, webSite, results);
				if(foundAnswer != null){
					correctAnswer = foundAnswer;
				}
			}else if(searchMethod == SEARCH_METHOD.EARLIEST){
				correctAnswer =	chooseEarliest(webSite, results);
			}
		}
		return correctAnswer;
	}

	private static String chooseEarliest(WebPage webSite, WebPageSearchResults results) {
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
	
	private static String chooseAnswerCorrect(String questionToSearch, WebPage webSite, WebPageSearchResults results) {
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
	
	public static boolean isValidSearchResult(int position, String searchedWord, WebPage page){
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
	
	public static String proccessQuestion(String question){
		String[] insignificantWords = {"òí", "îé", "äéä", "îä", "àéæä", "ìîä", "äëé", "áàéæå", "àéæå", "ìàéæå", "áàéæä", "àéæä", "ìàéæä", "àú", "àðé", "ùì", "àðé","àúä","àú","äåà","äéà","àðçðå","àúí","àúï","äí","äï","ìÀ","ìÄé","ìÀêÈ","ìÈêÀ","ìåÉ","ìÈäÌ","ìÈðåÌ","ìÈëÆí","ìÈëÆï","ìÈäÆí","ìÈäÆï","ùÆÑì","ùÆÑìÄé","ùÆÑìÀêÈ","ùÆÑìÈêÀ","ùÆÑìåÉ","ùÆÑìÈäÌ","ùÆÑìÈðåÌ","ùÆÑìÈëÆí","ùÆÑìÈëÆï","ùÆÑìÈäÆí","ùÆÑìÈäÆï","àÆú","àåÉúÄé","àåÉúÀêÈ","àåÉúÈêÀ","àåÉúåÉ","àåÉúÈäÌ","àåÉúÈðåÌ","àÆúÀëÆí","àÆúÀëÆï","àåÉúÈí","àåÉúÈï","òÄí - àÆú","àÄéúÄé","àÄéúÀêÈ","àÄéúÈêÀ","àÄéúÈêÀ","àÄéúÈäÌ","àÄéúÈðåÌ","àÄéúÀëÆí","àÄéúÀëÆï","àÄéúÈí","àÄéúÈï","áÀÌ","áÄÌé","áÀÌêÈ","áÈÌêÀ","áÌåÉ","áÈÌäÌ","áÈÌðåÌ","áÈÌëÆí","áÈÌëÆï","áÈÌäÆí","áÈÌäÆï","îÄ","îÄîÆðÄé","îÄîÀêÈ","îÄîÅêÀ","îÄîÆðåÌ","îÄîÆðÈäÄ","îÅàÄéúÈðåÌ","îÄîÆðåÌ","îÄëÆÌí","îÄëÆÌï","îÅäÆí","îÅäÆï","àÅöÆì","àÆöÀìÄé","àÆöÀìÀêÈ","àÆöÀìÅêÀ","àÆöÀìåÉ","àÆöÀìÈäÌ","àÆöÀìÅðåÌ","àÆöÀìÀëÆí","àÆöÀìÀëÆï","àÆöÀìÈí","àÆöÀìÈï","áÄÌùÀÑáÄéì","áÄÌùÀÑáÄéìÄé","áÄÌùÀÑáÄéìÀêÈ","áÄÌùÑáÄéìÅêÀ","áÄÌùÀÑáÄéìåÉ","áÄÌùÀÑáÄéìÈäÌ","áÄÌùÀÑáÄéìÅðåÌ","áÄÌùÀÑáÄéìÀëÆí","áÄÌùÀÑáÄéìÀëÆï","áÄÌùÀÑáÄéìÈí","áÄÌùÀÑáÄéìÈï","îÄñÈáÄéá","îÄñÀáÄéáÄé","îÄñÀáÄéáÀêÈ","îÄñÀáÄéáÅêÀ","îÄñÀáÄéáåÉ","îÄñÀáÄéáÈäÌ","îÄñÀáÄéáÅðåÌ","îÄñÀáÄéáÀëÆí","îÄñÀáÄéáÀëÆï","îÄñÀáÄéáÈí","îÄñÀáÄéáÈï","îåÌì","îåÌìÄé","îåÌìÀêÈ","îåÌìÅêÀ","îåÌìåÉ","îåÌìÈäÌ","îåÌìÅðåÌ","îåÌìÀëÆí","îåÌìÀëÆï","îåÌìÈí","îåÌìÈï","òÇì - éÇã","òÇì - éÈãÄé","òÇì - éÈãÀêÈ","òÇì - éÈãÅêÀ","òÇì - éÈãå","","òÇì - éÈãÈäÌ","òÇì – éÈãÅðåÌ","òÇì - éÆãÀëÆí","òÇì - éÆãÀëÆï","òÇì - éÈãÈí","òÇì – éÈãÈï","áÄÌâÀìÈì","áÄÌâÀìÈìÄé","áÄÌâÀìÈìÀêÈ","áÄÌâÀìÈìÅêÀ","áÄÌâÀìÈìåÉ","áÄÌâÀìÈìÈäÌ","áÄÌâÀìÈìÅðåÌ","áÄÌâÀìÇìÀëÆí","áÄÌâÀìÇìÀëÆï","áÄÌâÀìÈìÈí","áÄÌâÀìÈìÈï","áÀÌòÆöÆí","áÀÌòÇöÀîÄé","áÀÌòÇöÀîÀêÈ","áÀÌòÇöÀîÅêÀ","áÀÌòÇöÀîåÉ","áÀÌòÇöÀîÈäÌ","áÀÌòÇöÀîÅðåÌ","áÀÌòÇöÀîÀëÆí","áÀÌòÇöÀîÀëÆï","áÀÌòÇöÀîÈí","áÀÌòÇöÀîÈï","ëÀÌîåÉ","ëÈÌîåÉðÄé","ëÈÌîåÉêÈ","ëÈÌîåÉêÀ","ëÈÌîåÉäåÌ","ëÈÌîåÉäÈ","ëÈÌîåÉðåÌ","ëÀÌîåÉëÆí","ëÀÌîåÉëÆï","ëÀÌîåÉäÆí","ëÀÌîåÉäÆï","òÇì","òÈìÇéé","òÈìÆéêÈ","òÈìÇéÄêÀ","òÈìÈéå","òÈìÆéäÈ","òÈìÅéðåÌ","òÂìÅéëÆí","òÂìÅéëÆï","òÂìÅéäÆí","òÂìÅéäÆï","àÆì","àÅìÇéé","àÅìÆéêÈ","àÅìÇéÄéêÀ","àÅìÈéå","àÅìÆéäÈ","ìÄôÀðÅéäÆï","àÂìÅéëÆí","àÂìÅéëÆï","àÂìÅéäÆí","àÂìÅéäÆï","ìÄôÀðÅé","ìÀôÈðÇéé","ìÀôÈðÆéêÈ","ìÀôÈðÇéÄéêÀ","ìÀôÈðÈéå","ìÀôÈðÆéäÈ","ìÀôÈðÅéðåÌ","ìÄôÀðÅéëÆí","ìÄôÀðÅéëÆï","ìÄôÀðÅéäÆí","ìÄôÀðÅéäÆï","àÇçÂøÅé","àÇçÂøÇéé","àÇçÂøÆéêÈ","àÇçÂøÇéÄéêÀ","àÇçÂøÈéå","àÇçÂøÆéäÈ","àÇçÂøÅéðåÌ","àÇçÂøÅéëÆí","àÇçÂøÅéëÆï","àÇçÂøÅéäÆí","àÇçÂøÅéäÆï","òÇì – éÀãÅé","òÇì - éÈãÇéé","òÇì - éÈãÆéêÈ","òÇì - éÈãÇéÄéê","òÇì - éÈãÈéå","òÇì - éÈãÆéäÈ","òÇì – éÈãÅéðåÌ","òÇì - éÆãÀëÆí","òÇì - éÆãÀëÆï","òÇì - éÈãÈí","òÇì – éÈãÈï","îÅòÇì","îÅòÈìÇéé","îÅòÈìÆéêÈ","îÅòÈì","éÄéê","ùòìéê","ùòìéå","òìéê","îÅòÈìÈéå","îÅòÈìÆéäÈ","îÅòÈìÅéðåÌ","îÅòÂìÅéëÆí","îÅòÂìÅéëÆï","îÅòÂìÅéäÆí","îÅòÂìÅéäÆï","îÄúÇçÇú","îÄúÇçÀúÇéé","îÄúÇçÀúÆéêÈ","îÄúÇçÀúÇéÄéê","îÄúÇçÀúÈéå","îÄúÇçÀúÆéäÈ","îÄúÇçÀúÅéðåÌ","îÄúÇçÀúÅéëÆí","îÄúÇçÀúÅéëÆï","îÅúÇçÀúÅéäÆí","îÄúÇçÀúÅéäÆï","áÄÌìÀòÂãÅé","áÄÌìÀòÈãÇéé","áÄÌìÀòÈãÆéêÈ","áÄÌìÀòÈãÇéÄéêÀ","áÄÌìÀòÈãÈéå","áÄÌìÀòÈãÆéäÈ","áÄÌìÀòÈãÅéðåÌ","áÄÌìÀòÂãÅéëÆí","áÄÌìÀòÂãÅéëÆï","áÄÌìÀòÂãÅéäÆí","áÄÌìÀòÂãÅéäÆï"};
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
				// (îä ëúåá áñôø "áððä áððä")
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
				for (String insignificantWord : insignificantWords) {
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
	
	public static String[] processAnswers(String[] answers){
		String[] newAnswers = new String[answers.length];
		for(int i=0; i< answers.length; i++) {
			if(answers[i].length() <= 2){
				newAnswers[i] = " " + answers[i] + " ";
			}else{
				newAnswers[i] = answers[i];
			}
		}
		
		return newAnswers;
	}

	/**
	 * Fibonacci function 
	 * @param index
	 * @return The fibonacci value of the given index 
	 */
	public static long fib(int index) {
		if (index <= 1) {
			return index;
		} else {
			return fib(index - 1) + fib(index - 2);
		}
	}
	
	enum SEARCH_METHOD{
		NO_OCCURRENCE,
		MOST_COMPATIBLE,
		EARLIEST,
		LATEST
	}
}
