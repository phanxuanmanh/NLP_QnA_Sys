/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.processing;

import hcmuaf.nlp.core.dao.QuestionTypeDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionTypeDaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorDaoImpl;
import hcmuaf.nlp.core.model.QuestionType;
import hcmuaf.nlp.core.model.QuestionVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Class SparseArffFileWriter.
 */
public class SparseArffFileWriter {
	
	/** The Constant RELATION_PREFIX. */
	private static final String RELATION_PREFIX = "@relation";
	
	/** The Constant ATTRIBUTE_PREFIX. */
	private static final String ATTRIBUTE_PREFIX = "@attribute";
	
	/** The Constant WORD. */
	private static final String WORD = "w";
	
	/** The Constant NUMBER_TYPE. */
	private static final String NUMBER_TYPE = "numeric";
	
	/** The Constant SPACE. */
	private static final String SPACE = " ";
	
	/** The Constant DELIMITER. */
	private static final String DELIMITER = ",";
	
	/** The Constant CLASS_PREFIX. */
	private static final String CLASS_PREFIX = "Class";
	
	/** The Constant DATA_INDENTIFY. */
	private static final String DATA_INDENTIFY = "@data";
	
	/** The Constant CLOSE_DATA. */
	private static final String CLOSE_DATA = "}";
	
	/** The Constant OPEN_DATA. */
	private static final String OPEN_DATA = "{";
	
	/** The Constant RESOURCE_FOLDER. */
	private static final String RESOURCE_FOLDER = "resources/weka/";
	
	/** The Constant FILE_EXTENSION. */
	private static final String FILE_EXTENSION = ".arff";

	/**
	 * Write file.
	 *
	 * @param filename the filename
	 * @param listKeyWord the list key word
	 * @param listQuestion the list question
	 * @throws FileNotFoundException the file not found exception
	 */
	public void writeFile(String filename, List<Long> listKeyWord,
			List<Long> listQuestion) throws FileNotFoundException {
		QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();
		QuestionTypeDao typeDao = new QuestionTypeDaoImpl();
		PrintWriter pw = new PrintWriter(new File(RESOURCE_FOLDER + filename
				+ FILE_EXTENSION));
		HashMap<Long, Integer> wordWithOrder = new HashMap<Long, Integer>();
		for (int i = 0; i < listKeyWord.size(); i++) {
			wordWithOrder.put(listKeyWord.get(i), Integer.valueOf(i));
		}
		// relation declare
		pw.println(RELATION_PREFIX + SPACE + filename);
		// line break
		pw.println();
		// declare type
		for (int i = 0; i < listKeyWord.size(); i++) {
			String typeStr = ATTRIBUTE_PREFIX + SPACE + WORD
					+ String.valueOf(listKeyWord.get(i).intValue()) + SPACE
					+ NUMBER_TYPE;
			pw.println(typeStr);
		}
		// declare class type
		ArrayList<QuestionType> listType = typeDao.getQuestionTypeList();
		String classStr = getClassDeclareStr(listType);
		pw.println(classStr);
		// line break
		pw.println();
		//data indentify
		pw.println(DATA_INDENTIFY);
		// print data
		for (Long questionID : listQuestion) {
			ArrayList<QuestionVector> listVector = vectorDao
					.getListVector(questionID.intValue());
			int typeId = typeDao.getTypeOfQuestion(questionID);
			String vectorString = getLineData(wordWithOrder, listVector, typeId);
			pw.println(vectorString);
		}
		pw.flush();
		pw.close();
		System.out.println("finish write file");
	}

	/**
	 * Gets the line data.
	 *
	 * @param listKeyWord the list key word
	 * @param listVector the list vector
	 * @param typeId the type id
	 * @return the line data
	 */
	private String getLineData(HashMap<Long, Integer> listKeyWord,
			ArrayList<QuestionVector> listVector, int typeId) {
		StringBuilder vectorString = new StringBuilder();
		vectorString.append(OPEN_DATA);
		for (QuestionVector vector : listVector) {
			Integer position = listKeyWord.get(vector
					.getWordID());
			if (position != null) {
				String item = String.valueOf(position.intValue()) + SPACE
						+ String.valueOf(vector.getTfidf()) + DELIMITER;
				vectorString.append(item);
			}
		}
		if (typeId != 0) {
			String questionType = listKeyWord.size() + SPACE + CLASS_PREFIX
					+ String.valueOf(typeId);
			vectorString.append(questionType);
		}
		vectorString.append(CLOSE_DATA);
		return vectorString.toString();
	}

	/**
	 * Gets the class declare str.
	 *
	 * @param listTypes the list types
	 * @return the class declare str
	 */
	private String getClassDeclareStr(ArrayList<QuestionType> listTypes) {
		StringBuilder classDeclare = new StringBuilder();
		classDeclare.append(ATTRIBUTE_PREFIX);
		classDeclare.append(SPACE);
		classDeclare.append(CLASS_PREFIX);
		classDeclare.append(SPACE);
		classDeclare.append(OPEN_DATA);
		for (int i = 0; i < listTypes.size(); i++) {
			String classId = String.valueOf(listTypes.get(i).getTypeID());
			classDeclare.append(CLASS_PREFIX + classId);
			if (i < listTypes.size() - 1) {
				classDeclare.append(DELIMITER);
			}
		}
		classDeclare.append(CLOSE_DATA);
		return classDeclare.toString();
	}
}
