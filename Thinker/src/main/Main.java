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
			String searchInGoogle = "מי היה רמטכ\"ל צה\"ל וכבש את ירושלים במלחמת ששת הימים";
			String[] searchInWebPage = {"מנחם בגין", "משה דיין", "יצחק רבין"};
			
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

			String negativeWord = " לא ";
			if(Question.contains(negativeWord)){
				searchMethod = SEARCH_METHOD.NO_OCCURRENCE;
			}
			
			if(Question.contains(" מוקדם ")){
				searchMethod = SEARCH_METHOD.EARLIEST;
			}
			if(Question.contains(" מאוחר ")){
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
		String[] insignificantWords = {"עם", "מי", "היה", "מה", "איזה", "למה", "הכי", "באיזו", "איזו", "לאיזו", "באיזה", "איזה", "לאיזה", "את", "אני", "של", "אני","אתה","את","הוא","היא","אנחנו","אתם","אתן","הם","הן","לְ","לִי","לְךָ","לָךְ","לוֹ","לָהּ","לָנוּ","לָכֶם","לָכֶן","לָהֶם","לָהֶן","שֶׁל","שֶׁלִי","שֶׁלְךָ","שֶׁלָךְ","שֶׁלוֹ","שֶׁלָהּ","שֶׁלָנוּ","שֶׁלָכֶם","שֶׁלָכֶן","שֶׁלָהֶם","שֶׁלָהֶן","אֶת","אוֹתִי","אוֹתְךָ","אוֹתָךְ","אוֹתוֹ","אוֹתָהּ","אוֹתָנוּ","אֶתְכֶם","אֶתְכֶן","אוֹתָם","אוֹתָן","עִם - אֶת","אִיתִי","אִיתְךָ","אִיתָךְ","אִיתָךְ","אִיתָהּ","אִיתָנוּ","אִיתְכֶם","אִיתְכֶן","אִיתָם","אִיתָן","בְּ","בִּי","בְּךָ","בָּךְ","בּוֹ","בָּהּ","בָּנוּ","בָּכֶם","בָּכֶן","בָּהֶם","בָּהֶן","מִ","מִמֶנִי","מִמְךָ","מִמֵךְ","מִמֶנוּ","מִמֶנָהִ","מֵאִיתָנוּ","מִמֶנוּ","מִכֶּם","מִכֶּן","מֵהֶם","מֵהֶן","אֵצֶל","אֶצְלִי","אֶצְלְךָ","אֶצְלֵךְ","אֶצְלוֹ","אֶצְלָהּ","אֶצְלֵנוּ","אֶצְלְכֶם","אֶצְלְכֶן","אֶצְלָם","אֶצְלָן","בִּשְׁבִיל","בִּשְׁבִילִי","בִּשְׁבִילְךָ","בִּשׁבִילֵךְ","בִּשְׁבִילוֹ","בִּשְׁבִילָהּ","בִּשְׁבִילֵנוּ","בִּשְׁבִילְכֶם","בִּשְׁבִילְכֶן","בִּשְׁבִילָם","בִּשְׁבִילָן","מִסָבִיב","מִסְבִיבִי","מִסְבִיבְךָ","מִסְבִיבֵךְ","מִסְבִיבוֹ","מִסְבִיבָהּ","מִסְבִיבֵנוּ","מִסְבִיבְכֶם","מִסְבִיבְכֶן","מִסְבִיבָם","מִסְבִיבָן","מוּל","מוּלִי","מוּלְךָ","מוּלֵךְ","מוּלוֹ","מוּלָהּ","מוּלֵנוּ","מוּלְכֶם","מוּלְכֶן","מוּלָם","מוּלָן","עַל - יַד","עַל - יָדִי","עַל - יָדְךָ","עַל - יָדֵךְ","עַל - יָדו","","עַל - יָדָהּ","עַל – יָדֵנוּ","עַל - יֶדְכֶם","עַל - יֶדְכֶן","עַל - יָדָם","עַל – יָדָן","בִּגְלָל","בִּגְלָלִי","בִּגְלָלְךָ","בִּגְלָלֵךְ","בִּגְלָלוֹ","בִּגְלָלָהּ","בִּגְלָלֵנוּ","בִּגְלַלְכֶם","בִּגְלַלְכֶן","בִּגְלָלָם","בִּגְלָלָן","בְּעֶצֶם","בְּעַצְמִי","בְּעַצְמְךָ","בְּעַצְמֵךְ","בְּעַצְמוֹ","בְּעַצְמָהּ","בְּעַצְמֵנוּ","בְּעַצְמְכֶם","בְּעַצְמְכֶן","בְּעַצְמָם","בְּעַצְמָן","כְּמוֹ","כָּמוֹנִי","כָּמוֹךָ","כָּמוֹךְ","כָּמוֹהוּ","כָּמוֹהָ","כָּמוֹנוּ","כְּמוֹכֶם","כְּמוֹכֶן","כְּמוֹהֶם","כְּמוֹהֶן","עַל","עָלַיי","עָלֶיךָ","עָלַיִךְ","עָלָיו","עָלֶיהָ","עָלֵינוּ","עֲלֵיכֶם","עֲלֵיכֶן","עֲלֵיהֶם","עֲלֵיהֶן","אֶל","אֵלַיי","אֵלֶיךָ","אֵלַיִיךְ","אֵלָיו","אֵלֶיהָ","לִפְנֵיהֶן","אֲלֵיכֶם","אֲלֵיכֶן","אֲלֵיהֶם","אֲלֵיהֶן","לִפְנֵי","לְפָנַיי","לְפָנֶיךָ","לְפָנַיִיךְ","לְפָנָיו","לְפָנֶיהָ","לְפָנֵינוּ","לִפְנֵיכֶם","לִפְנֵיכֶן","לִפְנֵיהֶם","לִפְנֵיהֶן","אַחֲרֵי","אַחֲרַיי","אַחֲרֶיךָ","אַחֲרַיִיךְ","אַחֲרָיו","אַחֲרֶיהָ","אַחֲרֵינוּ","אַחֲרֵיכֶם","אַחֲרֵיכֶן","אַחֲרֵיהֶם","אַחֲרֵיהֶן","עַל – יְדֵי","עַל - יָדַיי","עַל - יָדֶיךָ","עַל - יָדַיִיך","עַל - יָדָיו","עַל - יָדֶיהָ","עַל – יָדֵינוּ","עַל - יֶדְכֶם","עַל - יֶדְכֶן","עַל - יָדָם","עַל – יָדָן","מֵעַל","מֵעָלַיי","מֵעָלֶיךָ","מֵעָל","יִיך","שעליך","שעליו","עליך","מֵעָלָיו","מֵעָלֶיהָ","מֵעָלֵינוּ","מֵעֲלֵיכֶם","מֵעֲלֵיכֶן","מֵעֲלֵיהֶם","מֵעֲלֵיהֶן","מִתַחַת","מִתַחְתַיי","מִתַחְתֶיךָ","מִתַחְתַיִיך","מִתַחְתָיו","מִתַחְתֶיהָ","מִתַחְתֵינוּ","מִתַחְתֵיכֶם","מִתַחְתֵיכֶן","מֵתַחְתֵיהֶם","מִתַחְתֵיהֶן","בִּלְעֲדֵי","בִּלְעָדַיי","בִּלְעָדֶיךָ","בִּלְעָדַיִיךְ","בִּלְעָדָיו","בִּלְעָדֶיהָ","בִּלְעָדֵינוּ","בִּלְעֲדֵיכֶם","בִּלְעֲדֵיכֶן","בִּלְעֲדֵיהֶם","בִּלְעֲדֵיהֶן"};
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