package test;
import org.junit.Assert;
import org.junit.Test;

import think.Thinker;

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
	
	@Test
	public void question8() {
		String question = "איזה גוף מפרסם בכל חודש את מדד המחירים לצרכן?";
		String[] answers = {"משרד האוצר", "בנק ישראל", "הלמ\"ס"};
		String correctAnswer = "הלמ\"ס";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question9() {
		String question = "\"טל הדבש\" הוא שם זן של...?";
		String[] answers = {"פסיפלורה", "מלון", "מנגו"};
		String correctAnswer = "מלון";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question10() {
		String question = "לפי הפסוק \"בנפל אויביך אל תשמח ובכשלו אל...\"";
		String[] answers = {"יצהל פיך", "יצחק קהלך", "יגל לבך"};
		String correctAnswer = "יגל לבך";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question11() {
		String question = "עבדו עליך במכולת וקיבלת שטר ישן שעליו דמותו של חיים ויצמן. באיזה ערך השטר?";
		String[] answers = {"500 לירות", "100 לירות", "50 לירות"};
		String correctAnswer = "50 לירות";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question12() {
		String question = "מיהו השחקן הראשון, ששמונה סרטים שלו ברציפות הכניסו יותר מ-100 מיליון דולר בארה\"ב בלבד?";
		String[] answers = {"הריסון פורד", "בראד פיט", "ארנולד שוורצנגר", "וויל סמית"};
		String correctAnswer = "וויל סמית";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question13() {
		String question = "על פי הקבלה מי אמורים להגיע במהלך החג לבקר בסוכה?";
		String[] answers = {"הסריסים", "האושפיזין", "האחשדרפנים"};
		String correctAnswer = "האושפיזין";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question14() {
		String question = "את מי לא סביר שאזמין להופיע ביומולדת של הבת שלי?";
		String[] answers = {"סבתא זפטה", "דוד חיים", "מיכל הקטנה"};
		String correctAnswer = "סבתא זפטה";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question15() {
		String question = "באיזו מדינה פעל והתפרסם דני האדום";
		String[] answers = {"צרפת", "איטליה", "הולנד", "בלגיה"};
		String correctAnswer = "צרפת";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question16() {
		String question = "מי מהלהיטים של סטטיק ובן אל יצא מוקדם יותר?";
		String[] answers = {"סלסולים", "ברבי", "כביש החוף"};
		String correctAnswer = "ברבי";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question17() {
		String question = "לאיזה חבל בספרד שייכת העיר סן סבסטיאן?";
		String[] answers = {"הבסקים", "גליסיה", "אנדלוסיה", "קטלוניה"};
		String correctAnswer = "הבסקים";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question18() {
		String question = "מי מבין המלחינים הקלאסיים הבאים מת צעיר יותר?";
		String[] answers = {"שוברט", "מוצארט", "באך"};
		String correctAnswer = "שוברט";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question19() {
		String question = "למי מהזמרים הבאים היו יותר שירים שנכנסו למצעד הבילבורד הוט 100?";
		String[] answers = {"מייקל ג\'קסון", "מדונה", "אלביס פרסלי", "ויטני יוסטון"};
		String correctAnswer = "אלביס פרסלי";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question20() {
		String question = "על פי הפסוק מההגדה: כל המרבה לספר ביציאת מצרים הרי זה...?";
		String[] answers = {"מסובך", "מדווח", "משובח"};
		String correctAnswer = "משובח";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question21() {
		String question = "לאן עלי לנסוע כדי לראות את סלע האולורו המכונה גם האיירס רוק?";
		String[] answers = {"קנדה", "אוסטרליה", "יפן"};
		String correctAnswer = "אוסטרליה";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question22() {
		String question = "באיזו מדינה אחוז הג\'ינג\'ים באוכלוסיה גבוה יותר?";
		String[] answers = {"אירלנד", "גרינלנד", "איסלנד"};
		String correctAnswer = "אירלנד";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	private void checkQuestion(String question,String[] possibleAnswers, String correctAnswer){
		boolean hasFailed = false;
		
		String foundAnswer = "";
		try {
			foundAnswer = Thinker.getInstance().AnswerMeThis(question, possibleAnswers);
		} catch (Exception e) {
			e.printStackTrace();
			hasFailed = true;
		}

		
		// assert statements
		Assert.assertFalse(hasFailed);
		Assert.assertEquals(correctAnswer, foundAnswer);
	}
}