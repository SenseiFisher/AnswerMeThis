import org.junit.Assert;
import org.junit.Test;

import main.Main;

public class Tests {

	@Test
	public void question1() {
		String question = "�� ��� ����\"� ��\"� ���� �� ������� ������ ��� �����";
		String[] answers = { "���� ����", "��� ����", "���� ����" };
		String correctAnswer = "���� ����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	
	@Test
	public void question2() {
		String question = "���� ���� �� ����� ������";
		String[] answers = {"����", "������", "�����"};
		String correctAnswer = "�����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question3() {
		String question = "�� ��� ������?";
		String[] answers = {"������ ������", "��� ���", "���� ����"};
		String correctAnswer = "������ ������";
		
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
