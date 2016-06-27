/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.dao.WikiInvertedIndexDao;
import hcmuaf.nlp.core.hibernateDao.impl.WikiInvertedIndexDaoImpl;
import hcmuaf.nlp.core.model.WikiConceptWord;
import hcmuaf.nlp.core.model.WikiInvertedIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Class WikiConceptVectorAccessor.
 */
public class WikiConceptVectorAccessor {
	
	/** The number of concept. */
	private static long numberOfConcept=0;
	
	/** The inverted index vector dao. */
	private static WikiInvertedIndexDao invertedIndexVectorDao;
	
	/** The list word freq. */
	private static HashMap<Integer, Integer> listWordFreq;
	
	/** The list page id. */
	private static List<Integer> listPageId;
	
	/** The list wiki con cept by word. */
	private static HashMap<Integer, List<WikiConceptWord>> listWikiConCeptByWord;

	/**
	 * Gets the inverted index vector dao.
	 *
	 * @return the inverted index vector dao
	 */
	public static WikiInvertedIndexDao getInvertedIndexVectorDao() {
		if (invertedIndexVectorDao == null) {
			invertedIndexVectorDao = new WikiInvertedIndexDaoImpl();
		}
		return invertedIndexVectorDao;
	}

	/**
	 * Gets the number of concept.
	 *
	 * @return the number of concept
	 */
	public static long getNumberOfConcept() {
		if (numberOfConcept == 0) {
			numberOfConcept = getInvertedIndexVectorDao()
					.currentNumberOfConcept();
			System.out.println("Finish load number of concept!!");
		}
		return numberOfConcept;
	}

	/**
	 * Gets the list concept id.
	 *
	 * @return the list concept id
	 */
	public static List<Integer> getListConceptId() {
		if (listPageId == null) {
			listPageId = getInvertedIndexVectorDao().getListPageId();
			System.out.println("Finish list concept Id!!");
		}
		return listPageId;
	}

	/**
	 * Gets the list word freq.
	 *
	 * @return the list word freq
	 */
	public static HashMap<Integer, Integer> getListWordFreq() {
		if (listWordFreq == null) {
			listWordFreq = getInvertedIndexVectorDao().getWordWithFreq();
			System.out.println("Finish load list of words with frequence!!");
		}
		return listWordFreq;
	}

	/**
	 * Number of concept contain word.
	 *
	 * @param wordID the word id
	 * @return the int
	 */
	public static int numberOfConceptContainWord(int wordID) {
		Integer numberOfConcept =getListWordFreq().get(Integer.valueOf(wordID));
		if(numberOfConcept==null){
			return 0;
		}else{
			return numberOfConcept.intValue();
		}
	}

	/**
	 * Gets the list wiki con cept by word.
	 *
	 * @return the list wiki con cept by word
	 */
	public static HashMap<Integer, List<WikiConceptWord>> getListWikiConCeptByWord() {
		if (listWikiConCeptByWord == null) {
			listWikiConCeptByWord = new HashMap<Integer, List<WikiConceptWord>>();
			long startTime = System.currentTimeMillis();
			System.out.println("start to get inverted index");
			List<WikiInvertedIndex> listWikiConceptWordVector = getInvertedIndexVectorDao().getAllVector();
			long step1 = System.currentTimeMillis();
			System.out.println("get list inverted index finish, time :"+ (step1-startTime));
			for (WikiInvertedIndex vector : listWikiConceptWordVector) {
				Integer wordId = Integer.valueOf(vector.getWordId());
				if (listWikiConCeptByWord.get(wordId) == null) {
					List<WikiConceptWord> newListVector = new ArrayList<WikiConceptWord>();
					newListVector.add(vector.toWikiConcept());
					listWikiConCeptByWord.put(wordId, newListVector);
				} else {
					List<WikiConceptWord> oldListVector = listWikiConCeptByWord
							.get(wordId);
					oldListVector.add(vector.toWikiConcept());
					listWikiConCeptByWord.put(wordId, oldListVector);
				}

			}
			long step2= System.currentTimeMillis();
			System.out.println("convert inverted index to hashmap finish, time :"+ (step2-step1));
		}
		return listWikiConCeptByWord;
	}

	/**
	 * Gets the list concept by word id.
	 *
	 * @param wordID the word id
	 * @return the list concept by word id
	 */
	public static List<WikiConceptWord> getListConceptByWordId(int wordID) {
		return getListWikiConCeptByWord().get(Integer.valueOf(wordID));
	}
}
