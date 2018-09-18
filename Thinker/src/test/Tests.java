package test;
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
		String question = "מי ראש הממשלה הנוכחי?";
		String[] answers = {"בנימין נתניהו", "משה פרץ", "יצחק רבין"};
		String correctAnswer = "בנימין נתניהו";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question4() {
		String question = "נכון ל-2017, באיזו עיר יש הכי הרבה מסעדות עם כוכבי מישלן";
		String[] answers = {"טוקיו", "רומא", "ניו-יורק", "פריס"};
		String correctAnswer = "טוקיו";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question5() {
		String question = "במהלך מחזור של 19 שנים, כמה פעמים מתרחשת שנה מעוברת?";
		String[] answers = {"3", "5", "7", "9"};
		String correctAnswer = "7";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question6() {
		String question = "אני רוצה לטוס מרומניה ישירות לישראל, לאיזו חברת תעופה עלי להתקשר?";
		String[] answers = {"טארום", "מאלב", "אירופלוט"};
		String correctAnswer = "טארום";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question7() {
		String question = "אם צבעתי את הקיר בסלון באינדיגו. כנראה שהקיר שלי...?";
		String[] answers = {"ירוק צהבהב", "אדום כתמתם", "כחול סגלגל"};
		String correctAnswer = "כחול סגלגל";
		
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
