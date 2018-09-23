package main;

import java.io.File;
import java.io.IOException;

import google.NoResultException;
import net.sourceforge.tess4j.Tesseract;
import think.Thinker;

public class Main {
	
	public static void main(String[] args) {
		try {
			String question = doOCR("pictures_for_ocr\\question.png").trim();
			String ans1 = doOCR("pictures_for_ocr\\ans1.png").trim();
			String ans2 = doOCR("pictures_for_ocr\\ans2.png").trim();
			String ans3 = doOCR("pictures_for_ocr\\ans3.png").trim();
			String ans4 = doOCR("pictures_for_ocr\\ans4.png").trim();
			String[] possiableAnswers = {ans1, ans2, ans3, ans4}; 
			
			// Print OCR results
			System.out.println(question);
			for (String ans : possiableAnswers) {
				System.out.println(ans);
			}
			
			
			String answer = Thinker.getInstance().AnswerMeThis(question, possiableAnswers);
			System.out.println(answer);
		} catch (IOException | NoResultException e) {
			e.printStackTrace();
		}
	}
	
	public static String doOCR(String filePath){
		String result = "";

		File question = new File(filePath);

		Tesseract instance = new Tesseract();
		// Set Tesseract data path to current project location
		instance.setDatapath("");
		instance.setLanguage("heb");

		try {
			result = instance.doOCR(question);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}