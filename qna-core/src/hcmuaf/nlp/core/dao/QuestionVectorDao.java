/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface QuestionVectorDao.
 */
public interface QuestionVectorDao {
	
	/**
	 * Gets the list key word.
	 *
	 * @return the list key word
	 */
	public List<Integer> getListKeyWord();

	/**
	 * Update tfidf.
	 *
	 * @param qid the qid
	 * @param wid the wid
	 * @param tfidf the tfidf
	 */
	public void updateTFIDF(int qid, int wid, double tfidf);

	/**
	 * Gets the list vector.
	 *
	 * @param questionID the question id
	 * @return the list vector
	 */
	public ArrayList<QuestionVector> getListVector(int questionID);

	/**
	 * Save word count.
	 *
	 * @param listWordWithFreq the list word with freq
	 * @param questionId the question id
	 */
	public void saveWordCount(HashMap<Integer, Integer> listWordWithFreq,
			int questionId);

	/**
	 * Update list vector.
	 *
	 * @param listWordVectors the list word vectors
	 */
	public void updateListVector(List<QuestionVector> listWordVectors);



	/**
	 * Read question vector data.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<QuestionVectorCsv> readQuestionVectorData()
			throws SQLException;

	/**
	 * Read question vector data.
	 *
	 * @param questionID the question id
	 * @return the question vector csv
	 * @throws SQLException the SQL exception
	 */
	public QuestionVectorCsv readQuestionVectorData(int questionID)
			throws SQLException;

	/**
	 * Gets the word with freq.
	 *
	 * @return the word with freq
	 */
	public HashMap<Integer, Integer> getWordWithFreq();

}
