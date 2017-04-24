/**
 * @author Manh Phan
 *
 * Created date Jun 21, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Interface QuestionVectorDao.
 */
public interface QuestionVectorDao {

	/**
	 * Gets the list key word.
	 *
	 * @return the list key word ID;
	 */
	public List<Long> getListKeyWord();

	/**
	 * Update TFIDF.
	 *
	 * @param questionId the question id
	 * @param wordId the word id
	 * @param tfidf the tfidf
	 */
	public void updateTFIDF(long questionId, long wordId, double tfidf);

	/**
	 * Gets the list vector.
	 *
	 * @param questionID
	 *            the question id
	 * @return the list vector
	 */
	public ArrayList<QuestionVector> getListVector(long questionID);

	/**
	 * Gets the all vector.
	 *
	 * @return the all vector
	 */
	public List<QuestionVector> getAllVector();

	/**
	 * Current number of vector.
	 *
	 * @return the number of vector
	 */
	public Long currentNumberOfVector();

	/**
	 * Save word count.
	 *
	 * @param listWordWithFreq
	 *            the list word with frequency
	 * @param questionId
	 *            the question id
	 */
	public void saveWordCount(Map<Long, Long> listWordWithFreq, long questionId);

	/**
	 * Update list vector.
	 *
	 * @param listWordVectors
	 *            the list word vectors
	 */
	public void updateListVector(List<QuestionVector> listWordVectors);

	/**
	 * Read question vector data.
	 *
	 * @return the array list
	 * @throws SQLException
	 *             the SQL exception
	 */
	public ArrayList<QuestionVectorCsv> readQuestionVectorData() throws SQLException;

	/**
	 * Read question vector data.
	 *
	 * @param questionID
	 *            the question id
	 * @return the question vector csv
	 * @throws SQLException
	 *             the SQL exception
	 */
	public QuestionVectorCsv readQuestionVectorData(long questionID) throws SQLException;

	/**
	 * Gets the word with frequency.
	 *
	 * @return the word with frequency
	 */
	public Map<Long, Integer> getWordWithFreq();

}
