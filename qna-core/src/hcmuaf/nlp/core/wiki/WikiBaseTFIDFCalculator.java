/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.dao.QuestionVectorBaseWikiDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorBaseWikiDaoImpl;
import hcmuaf.nlp.core.model.QuestionVectorBaseWiki;
import hcmuaf.nlp.core.model.WikiConceptWord;

import java.util.HashMap;
import java.util.List;

/**
 * The Class WikiBaseTFIDFCalculator.
 */
public class WikiBaseTFIDFCalculator {
	
	/** The question vector base wiki dao. */
	private QuestionVectorBaseWikiDao questionVectorBaseWikiDao;

	/**
	 * Instantiates a new wiki base tfidf calculator.
	 */
	public WikiBaseTFIDFCalculator() {
		questionVectorBaseWikiDao = new QuestionVectorBaseWikiDaoImpl();
	}

	/**
	 * Calculate tfidf.
	 */
	public void calculateTFIDF() {
		List<Integer> listQuestionId = questionVectorBaseWikiDao
				.getListQuestionId();
		for (int qid : listQuestionId) {
			List<QuestionVectorBaseWiki> listVector = questionVectorBaseWikiDao
					.getListVector(qid);
			if (listVector.size() > 0) {
				int numberOfWord = 0;
				for (QuestionVectorBaseWiki vector : listVector) {
					numberOfWord += vector.getFreq();
				}
				for (QuestionVectorBaseWiki vector : listVector) {
					int wordId = vector.getWordID();
					int freq = vector.getFreq();
					int numberOfConceptContainWord = WikiConceptVectorAccessor
							.numberOfConceptContainWord(Integer.valueOf(wordId));
					double weight = 0;
					if (numberOfConceptContainWord != 0) {
						weight = ((double) freq / numberOfWord)
								* Math.log10((double) WikiConceptVectorAccessor
										.getNumberOfConcept()
										/ numberOfConceptContainWord);
					}
					vector.setTfidf(weight);
				}
				questionVectorBaseWikiDao.updateListQuestionVector(listVector);
			}
		}
		System.out.println("finished update tfidf for all question");
	}

	/**
	 * Builds the interpretation vertor.
	 *
	 * @param questionID the question id
	 * @return the hash map
	 */
	public HashMap<Integer, Double> buildInterpretationVertor(int questionID) {
		HashMap<Integer, Double> interpretationVertor = new HashMap<Integer, Double>();
		List<QuestionVectorBaseWiki> listQuestionVector = questionVectorBaseWikiDao
				.getListVector(questionID);
		for (QuestionVectorBaseWiki questionVector : listQuestionVector) {
			int wordId = questionVector.getWordID();
			List<WikiConceptWord> listWikiConceptWordVector = WikiConceptVectorAccessor
					.getListConceptByWordId(wordId);
			if (listWikiConceptWordVector != null) {
				// weight of word in question
				double weight = questionVector.getTfidf();
				for (WikiConceptWord wikiConceptWordVector : listWikiConceptWordVector) {
					// weight of word in wikiconcept
					double tfidf = wikiConceptWordVector.getTfidf();
					Integer conceptId = Integer.valueOf(wikiConceptWordVector
							.getPageId());
					if (interpretationVertor.get(conceptId) == null) {
						interpretationVertor.put(conceptId,
								Double.valueOf(weight * tfidf));
					} else {
						double relationWeight = interpretationVertor.get(
								conceptId).doubleValue();
						interpretationVertor.put(
								conceptId,
								Double.valueOf(relationWeight
										+ (weight * tfidf)));
					}
				}
			}
		}
		return interpretationVertor;
	}

	/**
	 * Gets the list question id.
	 *
	 * @return the list question id
	 */
	public List<Integer> getListQuestionId() {
		return questionVectorBaseWikiDao.getListQuestionId();
	}
}
