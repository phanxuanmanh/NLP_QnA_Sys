/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.service.impl;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.service.QuestionComparator;
import hcmuaf.nlp.core.service.QuestionTFIDFService;
import hcmuaf.nlp.core.service.WikiConceptVectorAccessor;
import hcmuaf.nlp.core.service.WikiInterpretationVertorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class QuestionComparator.
 */
@Service("questionComparator")
public class QuestionComparatorImpl implements QuestionComparator {
	@Autowired
	private QuestionVectorDao questionVectorDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private QuestionTFIDFService questionTFIDFService;
	@Autowired
	private WikiInterpretationVertorService wikiInterpretationVertorService;
	@Autowired
	private WikiConceptVectorAccessor wikiConceptVectorAccessor;
	public QuestionComparatorImpl() {
	}

	@Override
	public double compareVector(Map<Integer, Double> vector1, Map<Integer, Double> vector2) {
		double EuclidLengthA = 0;
		double EuclidLengthB = 0;
		double dotProduct = 0;
		List<Integer> listPageId = new ArrayList<Integer>(wikiConceptVectorAccessor.getListConceptId());
		for (Integer pageId : listPageId) {
			Double weightA = vector1.get(pageId);
			Double weightB = vector2.get(pageId);
			if (weightA != null) {
				EuclidLengthA += Math.pow(weightA.doubleValue(), 2);
			}
			if (weightB != null) {
				EuclidLengthB += Math.pow(weightB.doubleValue(), 2);
			}
			if (weightA != null && weightB != null) {
				dotProduct += weightA.doubleValue() * weightB.doubleValue();
			}
		}
		if (EuclidLengthA == 0 || EuclidLengthB == 0) {
			return 0;
		}
		double relationValue = (dotProduct) / (Math.sqrt(EuclidLengthA) * Math.sqrt(EuclidLengthB));
		return relationValue;
	}

	@Override
	public HashMap<Long, Double> getRelationList(long questionId) {
		questionTFIDFService.calculateTFIDF(questionId);
		Map<Integer, Double> interpretationVertor = wikiInterpretationVertorService.buildInterpretationVector(questionId);
		HashMap<Long, Double> relationList = new HashMap<Long, Double>();
		
		List<Long> listQuestionInDB = questionDao.getAvailableQuestionList();
		for (Long comparedQuestion : listQuestionInDB) {
			if (comparedQuestion.intValue() != questionId) {
				Map<Integer, Double> compareInterpretationVertor = wikiInterpretationVertorService
						.buildInterpretationVector(comparedQuestion);
				double relationValue = compareVector(interpretationVertor, compareInterpretationVertor);
				relationList.put(comparedQuestion, Double.valueOf(relationValue));
			}
		}
		return relationList;
	}

}
