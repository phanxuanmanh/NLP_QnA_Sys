/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.service.impl;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.processing.QuestionWordAccessor;
import hcmuaf.nlp.core.service.QuestionTFIDFService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionTFIDFPServiceImpl implements QuestionTFIDFService {

	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionVectorDao vectorDao;
	
	@Autowired
	private QuestionWordAccessor questionWordAccessor;

	public QuestionTFIDFPServiceImpl() {
		
	}

	@Override
	public void calculateTFIDF() {
		List<Long> listQuestionId = questionDao.getAvailableQuestionList();
		for (long qid : listQuestionId) {
			calculateTFIDF(qid);
		}
	}

	@Override
	public List<QuestionVector> calculateTFIDF(long questionId) {
		int numOfQuestion = questionDao.countQuestion();
		List<QuestionVector> listVector = vectorDao.getListVector(questionId);
		if (listVector.size() > 0) {
			int numberOfWord = 0;
			for (QuestionVector vector : listVector) {
				numberOfWord += vector.getFreq();
			}
			for (QuestionVector vector : listVector) {
				long wordId = vector.getWordID();
				int freq = vector.getFreq();
				int numberOfQuestionContainWord = questionWordAccessor.getNumberOfQuestionContainWord(wordId);
				double weight = 0;
				if (numberOfQuestionContainWord > 0 && numberOfWord > 0) {
					weight = ((double) freq / numberOfWord)
							* Math.log10((double) numOfQuestion / numberOfQuestionContainWord);
				}
				vector.setTfidf(weight);
			}
			vectorDao.updateListVector(listVector);
		}
		return listVector;
	}

}
