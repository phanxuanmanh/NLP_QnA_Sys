package hcmuaf.nlp.core.processing;

import java.util.Map;

public interface QuestionWordAccessor {
	/**
	 * Gets the list word frequency.
	 *
	 * @return the list word frequency
	 */
	public abstract Map<Long, Integer> getListWordFreq();
	
	/**
	 * Gets the number of question contain word.
	 *
	 * @param wordId
	 *            the word id
	 * @return the number of question contain word
	 */
	public abstract int getNumberOfQuestionContainWord(long wordId);
	
}