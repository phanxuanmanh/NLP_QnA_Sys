/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.rest.resource;

import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.model.DemoInterpretationVertor;
import hcmuaf.nlp.core.processing.KeyWordFinder;
import hcmuaf.nlp.core.service.QuestionTFIDFService;
import hcmuaf.nlp.core.service.WikiInterpretationVertorService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class DemoController for Demo.
 */
@RestController
public class DemoController {
	
	/** The wiki interpretation vertor service. */
	@Autowired
	private WikiInterpretationVertorService wikiInterpretationVertorService;
	
	/** The question dao. */
	@Autowired
	private QuestionDao questionDao;
	
	/** The wiki concept dao. */
	@Autowired
	private WikiConceptDao wikiConceptDao;
	
	/** The keyword finder. */
	@Autowired
	private KeyWordFinder keywordFinder;
	
	/** The question TFIDF service. */
	@Autowired
	private QuestionTFIDFService questionTFIDFService;
	
	
	/**
	 * Gets the relate wiki concept.
	 *
	 * @param question the question
	 * @return the relate wiki concepts
	 */
	@RequestMapping(value = "/demo/getRelation/", method = RequestMethod.POST)
	public ResponseEntity<List<DemoInterpretationVertor>> getRelateWikiConcept(@RequestBody String question) {
		Long questionId = questionDao.insertQuestion(question, null);
		keywordFinder.findKeyWordForQuestion(questionId.intValue());
		questionTFIDFService.calculateTFIDF(questionId);
		Map<Integer, Double> interpretationVertor = wikiInterpretationVertorService
				.buildInterpretationVector(questionId);
		if (CollectionUtils.isEmpty(interpretationVertor)) {
			return new ResponseEntity<List<DemoInterpretationVertor>>(HttpStatus.NO_CONTENT);
		}
		List<DemoInterpretationVertor> relationList = interpretationVertor.entrySet().parallelStream().map(entry -> {
			DemoInterpretationVertor demoVector = new DemoInterpretationVertor();
			String pageTitle= wikiConceptDao.getPageTitleByRefPageId(entry.getKey());
			demoVector.setId(entry.getKey());
			demoVector.setConceptName(pageTitle);
			demoVector.setWeight(entry.getValue());
			return demoVector;
		}).sorted(Comparator.comparing(DemoInterpretationVertor::getWeight)).collect(Collectors.toList());
		return new ResponseEntity<List<DemoInterpretationVertor>>(relationList, HttpStatus.OK);
	}
}
