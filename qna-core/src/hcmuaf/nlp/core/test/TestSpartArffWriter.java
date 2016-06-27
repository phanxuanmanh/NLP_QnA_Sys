package hcmuaf.nlp.core.test;

import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorDaoImpl;
import hcmuaf.nlp.core.processing.SparseArffFileWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSpartArffWriter {
	public static void main(String[] args) {
		ArrayList<Integer> listQuestion = new ArrayList<Integer>();
		listQuestion.add(1);
		listQuestion.add(2);
		listQuestion.add(3);
		QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();
		List<Integer> listKeyWord = vectorDao.getListKeyWord();
		System.out.println(listKeyWord.size());
		Collections.sort(listKeyWord);
		SparseArffFileWriter fileWriter = new SparseArffFileWriter();
		try {
			fileWriter.writeFile("Test", listKeyWord, listQuestion);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
