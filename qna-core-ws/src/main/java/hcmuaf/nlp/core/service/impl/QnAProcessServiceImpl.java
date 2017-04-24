package hcmuaf.nlp.core.service.impl;

import hcmuaf.nlp.core.dao.AnswerDao;
import hcmuaf.nlp.core.dao.QnADao;
import hcmuaf.nlp.core.dao.QnASystemConfigDao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.Answer;
import hcmuaf.nlp.core.model.QnAPairEntity;
import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.model.QuestionRelateMapEntryComparator;
import hcmuaf.nlp.core.processing.KeyWordFinder;
import hcmuaf.nlp.core.service.QnAProcessService;
import hcmuaf.nlp.core.service.QuestionComparator;
import hcmuaf.nlp.core.util.QnASystemConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("processService")
public class QnAProcessServiceImpl implements QnAProcessService {
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private AnswerDao answerDao;
	@Autowired
	private QnADao qnaDao;
	@Autowired
	private KeyWordFinder keywordFinder;
	@Autowired
	private QuestionComparator comparator;
	@Autowired
	private QnASystemConfigDao qnASystemConfigDao;
	
	@Override
	public List<QnAPair> getListRelateQnA(String question) {
		Double minRelationWeight = Optional.ofNullable(
				qnASystemConfigDao
				.getDoubleConfigValue(QnASystemConstant.MIN_QUESTION_RELATE_WEIGHT_THRESURE_CONFIG_NAME))
				.orElse(QnASystemConstant.MIN_QUESTION_RELATE_WEIGHT_THRESURE_DEFAULT);
		long start = System.currentTimeMillis();
		Long questionId = questionDao.insertQuestion(question, null);
		System.out.println(questionId.intValue());
		keywordFinder.findKeyWordForQuestion(questionId.intValue());
		HashMap<Long, Double> relationList = comparator.getRelationList(questionId.intValue());
		List<Entry<Long, Double>> sortedRelationList =
				new ArrayList<Entry<Long, Double>>(relationList.entrySet());
		Collections.sort(sortedRelationList, new QuestionRelateMapEntryComparator().reversed());
		List<QnAPair> listQnA = new ArrayList<QnAPair>();
		QnAPair rootQnA = new QnAPair();
		rootQnA.setQuestionId(questionId);
		rootQnA.setQuestion(question);
		listQnA.add(0, rootQnA);
		
		for (int i = 0; i  < 10; i++) {
			if(i==sortedRelationList.size()){
				break;
			}
			Entry<Long, Double> entry = sortedRelationList.get(i);
			Long relateQuestionId = entry.getKey();
			Double weight = entry.getValue();
			if (weight >= minRelationWeight) {
				System.out.println(weight);
				List<QnAPairEntity> qnaPairEntities = qnaDao.getQnAPairs(relateQuestionId);
				for (QnAPairEntity e : qnaPairEntities) {
					if (e.getQuestion() != null && e.getAnswer() != null) {
						QnAPair pair = convertQnAEntityToQnaPair(e);
						listQnA.add(pair);
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("total time consume  " + (end - start));
		return listQnA;
	}

	private QnAPair convertQnAEntityToQnaPair(QnAPairEntity e) {
		QnAPair pair = new QnAPair();
		Long qID = e.getQuestion();
		Long aID = e.getAnswer();
		String questionContent = questionDao.getQuestionContent(qID.intValue());
		String anserContent = answerDao.getAnswer(aID).getContent();
		pair.setAnswerId(aID);
		pair.setQuestionId(qID);
		pair.setQuestion(questionContent);
		System.out.println(questionContent);
		pair.setAnswer(anserContent);
		return pair;
	}
	
	@Override
	public void addAnswer(long questionId, String answer) {
		if (answer != null && questionId > 0) {
			Long answerId = answerDao.insertAnswer(answer);
			QnAPairEntity qnaEntity = new QnAPairEntity();
			qnaEntity.setAnswer(answerId);
			qnaEntity.setQuestion(Long.valueOf(questionId));
			qnaDao.insertQnAPair(qnaEntity);
		}
	}
	

	
	@Override
	public List<QnAPair> getQnAByQuestionId(Long questionId) {
		List<QnAPairEntity> qnaPairEntities = qnaDao.getQnAPairs(questionId);
		List<QnAPair> listQnA = new ArrayList<QnAPair>();
		for (QnAPairEntity e : qnaPairEntities) {
			if (e.getQuestion() != null && e.getAnswer() != null) {
				QnAPair pair = convertQnAEntityToQnaPair(e);
				listQnA.add(pair);
			}
		}
		return listQnA;
	}
	
	@Override
	public List<Answer> getAnswerByQuestionId(Long questionId) {
		return qnaDao.getAnswersByQuestionId(questionId);
	}
	
	@Override
	public Question getQuestionById(Long questionId) {
		return questionDao.getQuestionByID(questionId);
	}
}
