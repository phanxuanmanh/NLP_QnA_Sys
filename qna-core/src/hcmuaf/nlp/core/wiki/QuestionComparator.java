/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.wiki;

import java.util.HashMap;
import java.util.List;

/**
 * The Class QuestionComparator.
 */
public class QuestionComparator {

	public double compareVector(HashMap<Integer, Double> vector1,
			HashMap<Integer, Double> vector2) {
		List<Integer> listPageId = WikiConceptVectorAccessor.getListConceptId();
		double sumA = 0;
		double sumB = 0;
		double sumAandB = 0;
		for (Integer pageId : listPageId) {
			Double weightA = vector1.get(pageId);
			Double weightB = vector2.get(pageId);
			if (weightA != null) {
				sumA += Math.pow(weightA.doubleValue(), 2);
			}
			if (weightB != null) {
				sumB += Math.pow(weightB.doubleValue(), 2);
			}
			if (weightA != null && weightB != null) {
				sumAandB += weightA.doubleValue() * weightB.doubleValue();
			}
		}
		if(sumA==0||sumB==0){
			return 0;
		}
		double relationValue = (sumAandB) / (Math.sqrt(sumA) * Math.sqrt(sumB));
		return relationValue;
	}

	public HashMap<Integer, Double> getRelationList(int questionId) {
		System.out.println("start to conpare");
		WikiBaseTFIDFCalculator calculator = WikiBaseTFIDFCalculatorGenerator.getCurrentCalculator();
		HashMap<Integer, Double> interpretationVertor = calculator
				.buildInterpretationVertor(questionId);
		HashMap<Integer, Double> relationList = new HashMap<Integer, Double>();
		List<Integer> listQuestionInDB = calculator.getListQuestionId();
		for (Integer comparedQuestion : listQuestionInDB) {
			if (comparedQuestion.intValue() != questionId) {
				HashMap<Integer, Double> compareInterpretationVertor = calculator
						.buildInterpretationVertor(comparedQuestion.intValue());
				double relationValue = compareVector(interpretationVertor,
						compareInterpretationVertor);
				relationList.put(comparedQuestion,
						Double.valueOf(relationValue));
			}
		}
		return relationList;
	}
}
