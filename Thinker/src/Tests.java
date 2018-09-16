import org.junit.Assert;
import org.junit.Test;

import main.Main;

public class Tests {

	@Test
	public void question1() {
		String question = "מי היה רמטכ\"ל צה\"ל וכבש את ירושלים במלחמת ששת הימים";
		String[] answers = { "מנחם בגין", "משה דיין", "יצחק רבין" };
		String correctAnswer = "יצחק רבין";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	
	@Test
	public void question2() {
		String question = "איזה מאכל לא הומצא בישראל";
		String[] answers = {"במבה", "פתיתים", "קרמבו"};
		String correctAnswer = "קרמבו";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question3() {
		String question = "מי ראש הממשלה?";
		String[] answers = {"בנימין נתניהו", "משה פרץ", "יצחק רבין"};
		String correctAnswer = "בנימין נתניהו";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	private void checkQuestion(String question,String[] possibleAnswers, String correctAnswer){
		boolean hasFailed = false;
		
		String foundAnswer = "";
		try {
			foundAnswer = Main.AnswerMeThis(question, possibleAnswers);
		} catch (Exception e) {
			e.printStackTrace();
			hasFailed = true;
		}

		
		// assert statements
		Assert.assertFalse(hasFailed);
		Assert.assertEquals(correctAnswer, foundAnswer);
	}
}
