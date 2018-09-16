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
//			String seachInGoogle = "איזה מאכל לא הומצא בישראל";
//			String[] searchInWebPage = {"במבה", "פתיתים", "קרמבו"};
//			String seachInGoogle = "מי ראש הממשלה?";
//			String[] searchInWebPage = {"בנימין נתניהו", "משה פרץ", "יצחק רבין"};
			String seachInGoogle = "מי היה רמטכל צהל וכבש את ירושלים במלחמת ששת הימים";
			String[] searchInWebPage = {"מנחם בגין", "משה דיין", "יצחק רבין"};
			
			
			boolean searchForNoOccurrences = false;
			String negativeWord = " לא ";
			if(seachInGoogle.contains(negativeWord)){
				searchForNoOccurrences = true;
			}
			
			Google.getInstance().Search(seachInGoogle);
			WebPage webSite = Google.getInstance().getNextResult().enter();
			WebPageSearchResults results = webSite.search(searchInWebPage);
			
			
			ArrayList<String> zeroOccurrences = new ArrayList<String>();
			ArrayList<String> moreOccurrences = new ArrayList<String>();
			for (String searchWord : searchInWebPage) {
				if(results.getOccurrences(searchWord).size() == 0){
					zeroOccurrences.add(searchWord);					
				}else{
					moreOccurrences.add(searchWord);
				}				
			}
			
			String answer = "No Answer";
			if (searchForNoOccurrences && zeroOccurrences.size() == 1) {
				answer = zeroOccurrences.get(0);
			} else if (!searchForNoOccurrences) {
				int maxPoints = 0;
				for (String word : moreOccurrences) {
					int wordPoints = 0;
					for (Integer occurrence : results.getOccurrences(word)) {
						for (String wordInQuestion : seachInGoogle.split(" ")) {
							if(webSite.isWordCloseToPosition(" " + wordInQuestion + " ", occurrence)){
								wordPoints += 3;
							}						
						}
						if(wordPoints > maxPoints){
							maxPoints = wordPoints;
							answer = word;
						}
					}
				}
			}
			System.out.println(answer);
		} catch (IOException | NoResultException e) {
			e.printStackTrace();
		}
	}
}
