package main;

import java.io.IOException;
import java.util.ArrayList;

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
		boolean searchForNoOccurrences = false;
		String negativeWord = " לא ";
		if(Question.contains(negativeWord)){
			searchForNoOccurrences = true;
		}
		String questionToSearch = proccessQuestion(Question);
		Google.getInstance().Search(questionToSearch);
		WebPage webSite = Google.getInstance().getNextResult().enter();
		WebPageSearchResults results = webSite.search(true, Answers);
		
		
		ArrayList<String> zeroOccurrences = new ArrayList<String>();
		ArrayList<String> moreOccurrences = new ArrayList<String>();
		for (String searchWord : Answers) {
			if(results.getOccurrences(searchWord).size() == 0){
				zeroOccurrences.add(searchWord);					
			}else{
				moreOccurrences.add(searchWord);
			}				
		}
		
		String correctAnswer = "No Answer";
		// If we search for no occurrences and there is only one value without occurrences,
		// Then that our answer.
		if (searchForNoOccurrences && zeroOccurrences.size() == 1) {
			correctAnswer = zeroOccurrences.get(0);
		} else if (!searchForNoOccurrences) {
				String foundAnswer = chooseAnswerCorrect(questionToSearch, webSite, results);
				if(foundAnswer != null){
					correctAnswer = foundAnswer;
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
	
	public static String proccessQuestion(String question){
		String[] insignificantWords = {"עם", "מי", "היה", "מה", "איזה", "למה", "הכי", "באיזו", "איזו", "את"};
		StringBuilder newQuestion = new StringBuilder(); 
		
		// Remove insignificant words
		for (String wordOfQuestion : question.split(" ")) {
			boolean isImportant = true;
			for (String insignificantWord : insignificantWords) {
				if(insignificantWord.equals(wordOfQuestion)){
					isImportant = false;
					break;
				}
			}
			
			if(isImportant){
				newQuestion.append(wordOfQuestion).append(" ");
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
}
