package hcmuaf.nlp.core.runnable;

import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorDaoImpl;
import hcmuaf.nlp.core.processing.SparseArffFileWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSpartArffWriter {
	public static void main(String[] args) {
		ArrayList<Long> listQuestion = new ArrayList<Long>();
		listQuestion.add(1L);
		listQuestion.add(2L);
		listQuestion.add(3L);
		QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();
		List<Long> listKeyWord = vectorDao.getListKeyWord();
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
