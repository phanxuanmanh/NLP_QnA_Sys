/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.service.impl;

import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.model.Tuple;
import hcmuaf.nlp.core.service.WikiConceptVectorAccessor;
import hcmuaf.nlp.core.service.WikiInterpretationVertorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class WikiBaseTFIDFCalculator.
 */
@Service
public class WikiInterpretationVertorServiceImpl implements WikiInterpretationVertorService {
	@Autowired
	private QuestionVectorDao questionVectorDao;
	@Autowired
	private WikiConceptVectorAccessor wikiConceptVectorAccessor;
	private Map<Long, Map<Integer, Double>> interpretationVectorData;
	
	private static final double RELATION_THRESHOLD = 1.0d;
	
	public WikiInterpretationVertorServiceImpl() {
	}
	
	@Override
	public Map<Integer, Double> buildInterpretationVector(long questionID) {
		if (interpretationVectorData == null) {
			interpretationVectorData = new HashMap<Long, Map<Integer, Double>>();
		}
		if (interpretationVectorData.get(questionID) != null) {
			return interpretationVectorData.get(questionID);
		} else {
			List<QuestionVector> listQuestionVector = questionVectorDao.getListVector(questionID);
			Map<Integer, Double> interpretationVector = buildInterpretationVector(listQuestionVector);
			interpretationVectorData.put(questionID, interpretationVector);
			return interpretationVector;
		}
	/*	List<QuestionVector> listQuestionVector = questionVectorDao.getListVector(questionID);
		Map<Integer, Double> interpretationVector = buidInterpretationVector(listQuestionVector);
		return interpretationVector;*/
	}
	
	@Override
	public Map<Integer, Double> buildInterpretationVector(List<QuestionVector> questionTFIDFVector) {
		HashMap<Integer, Double> interpretationVertor = new HashMap<Integer, Double>();
		for (QuestionVector questionVector : questionTFIDFVector) {
			long wordId = questionVector.getWordID();
			double weight = questionVector.getTfidf();
			List<Tuple<Integer, Double>> listWikiConceptWordVector = wikiConceptVectorAccessor
					.getListConceptByWordId(wordId);
			if (listWikiConceptWordVector != null) {
				for (Tuple<Integer, Double> wikiConceptWordVector : listWikiConceptWordVector) {
					double tfidf = wikiConceptWordVector.y;
					Integer conceptId = wikiConceptWordVector.x;
					if (interpretationVertor.get(conceptId) == null) {
						interpretationVertor.put(conceptId, Double.valueOf(weight * tfidf));
					} else {
						double relationWeight = interpretationVertor.get(conceptId).doubleValue();
						interpretationVertor.put(conceptId, Double.valueOf(relationWeight + (weight * tfidf)));
					}
				}
			}
		}
		Map<Integer, Double> filteredVector = interpretationVertor.entrySet().stream()
				.filter(entry -> entry.getValue() < RELATION_THRESHOLD)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
		return filteredVector ;
	}
}
