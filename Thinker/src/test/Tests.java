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
	
	@Test
	public void question8() {
		String question = "���� ��� ����� ��� ���� �� ��� ������� �����?";
		String[] answers = {"���� �����", "��� �����", "���\"�"};
		String correctAnswer = "���\"�";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question9() {
		String question = "\"�� ����\" ��� �� �� ��...?";
		String[] answers = {"��������", "����", "����"};
		String correctAnswer = "����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question10() {
		String question = "��� ����� \"���� ������ �� ���� ������ ��...\"";
		String[] answers = {"���� ���", "���� ����", "��� ���"};
		String correctAnswer = "��� ���";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question11() {
		String question = "���� ���� ������ ������ ��� ��� ����� ����� �� ���� �����. ����� ��� ����?";
		String[] answers = {"500 �����", "100 �����", "50 �����"};
		String correctAnswer = "50 �����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question12() {
		String question = "���� ����� ������, ������ ����� ��� ������� ������ ���� �-100 ������ ���� ����\"� ����?";
		String[] answers = {"������ ����", "���� ���", "������ ��������", "���� ����"};
		String correctAnswer = "���� ����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question13() {
		String question = "�� �� ����� �� ������ ����� ����� ��� ���� �����?";
		String[] answers = {"�������", "���������", "����������"};
		String correctAnswer = "���������";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question14() {
		String question = "�� �� �� ���� ������ ������ �������� �� ��� ���?";
		String[] answers = {"���� ����", "��� ����", "���� �����"};
		String correctAnswer = "���� ����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question15() {
		String question = "����� ����� ��� ������� ��� �����";
		String[] answers = {"����", "������", "�����", "�����"};
		String correctAnswer = "����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question16() {
		String question = "�� �������� �� ����� ��� �� ��� ����� ����?";
		String[] answers = {"�������", "����", "���� ����"};
		String correctAnswer = "����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question17() {
		String question = "����� ��� ����� ����� ���� �� �������?";
		String[] answers = {"������", "������", "��������", "�������"};
		String correctAnswer = "������";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question18() {
		String question = "�� ���� �������� �������� ����� �� ���� ����?";
		String[] answers = {"�����", "������", "���"};
		String correctAnswer = "�����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question19() {
		String question = "��� ������� ����� ��� ���� ����� ������ ����� �������� ��� 100?";
		String[] answers = {"����� �\'����", "�����", "����� �����", "����� ������"};
		String correctAnswer = "����� �����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question20() {
		String question = "�� �� ����� ������: �� ����� ���� ������ ����� ��� ��...?";
		String[] answers = {"�����", "�����", "�����"};
		String correctAnswer = "�����";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question21() {
		String question = "��� ��� ����� ��� ����� �� ��� ������� ������ �� ������ ���?";
		String[] answers = {"����", "��������", "���"};
		String correctAnswer = "��������";
		
		checkQuestion(question, answers, correctAnswer);
	}
	
	@Test
	public void question22() {
		String question = "����� ����� ���� ��\'���\'�� ��������� ���� ����?";
		String[] answers = {"������", "�������", "������"};
		String correctAnswer = "������";
		
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
