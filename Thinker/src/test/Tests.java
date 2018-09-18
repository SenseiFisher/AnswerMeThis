package test;
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
		String question = "�� ��� ������ ������?";
		String[] answers = {"������ ������", "��� ���", "���� ����"};
		String correctAnswer = "������ ������";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question4() {
		String question = "���� �-2017, ����� ��� �� ��� ���� ������ �� ����� �����";
		String[] answers = {"�����", "����", "���-����", "����"};
		String correctAnswer = "�����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question5() {
		String question = "����� ����� �� 19 ����, ��� ����� ������ ��� ������?";
		String[] answers = {"3", "5", "7", "9"};
		String correctAnswer = "7";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question6() {
		String question = "��� ���� ���� ������� ������ ������, ����� ���� ����� ��� ������?";
		String[] answers = {"�����", "����", "��������"};
		String correctAnswer = "�����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question7() {
		String question = "�� ����� �� ���� ����� ��������. ����� ����� ���...?";
		String[] answers = {"���� �����", "���� �����", "���� �����"};
		String correctAnswer = "���� �����";
		
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
