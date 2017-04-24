package hcmuaf.nlp.core.service.impl;

import hcmuaf.nlp.core.dao.AnswerDao;
import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.dao.QnADao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionHistoryDao;
import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.model.Answer;
import hcmuaf.nlp.core.model.QnAPairEntity;
import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.model.QuestionHistory;
import hcmuaf.nlp.core.model.QuestionVector;
import hcmuaf.nlp.core.processing.KeyWordFinder;
import hcmuaf.nlp.core.service.QnAHistoryService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import edu.stanford.nlp.ling.WordTag;

@Service("qnaHistoryService")
public class QnAHistoryServiceImpl implements QnAHistoryService {
	@Autowired
	private QuestionHistoryDao historyDao;
	@Autowired
	private KeyWordFinder keywordFinder;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private AnswerDao answerDao;
	@Autowired
	private QnADao qnaDao;
	@Autowired
	private KeyWordDao keyWordDao;
	@Autowired
	private QuestionVectorDao questionVectorDao;
	
	@Override
	public List<QnAPair> getQuestionHistory(Date startDate, String typeName, String keyWord, long size) {
		System.out.println("start search history");
		if (startDate == null || startDate.getTime() > (new Date().getTime())) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -1);
			startDate = calendar.getTime();
		}
		List<QnAPair> returnList = new ArrayList<QnAPair>();
		List<Question> listCommonQuestion = historyDao.getRecentQuestions(startDate);
		if (keyWord.length() > 1) {
			listCommonQuestion = filterQuestionByKeyWord(listCommonQuestion, keyWord);
		}
		for (Question question : listCommonQuestion) {
			List<QnAPairEntity> qnaPairEntities = qnaDao.getQnAPairs(question.getId());
			for (QnAPairEntity e : qnaPairEntities) {
				if (e.getQuestion() != null && e.getAnswer() != null) {
					QnAPair pair = new QnAPair();
					Long qID = e.getQuestion();
					Long aID = e.getAnswer();
					String questionContent = questionDao.getQuestionContent(qID.intValue());
					String anserContent = answerDao.getAnswer(aID).getContent();
					pair.setAnswerId(aID);
					pair.setQuestionId(qID);
					pair.setQuestion(questionContent);
					pair.setAnswer(anserContent);
					returnList.add(pair);
				}
			}
		}
		return returnList.stream().limit(size).collect(Collectors.toList());
	}
	
	private List<Question> filterQuestionByKeyWord(List<Question> inputQuestions, String keyWord) {
		String[] keyWordList = keyWord.split(",");
		List<WordTag> tagWords = keywordFinder.findKeyWord(keyWordList);
		List<Long> listExistingQuestionKeyWord = questionVectorDao.getListKeyWord();
		Set<Long> inputKeyWords = tagWords.stream().map(wordTag -> keyWordDao.getKeyWordId(wordTag.value()))
				.filter(listExistingQuestionKeyWord::contains).collect(Collectors.toSet());
		if (!CollectionUtils.isEmpty(inputKeyWords)) {
			List<QuestionVector> allVector = questionVectorDao.getAllVector();
			Set<Long> mappedQuestionIds = allVector.stream()
					.filter(vector -> inputKeyWords.contains(vector.getWordID())).map(QuestionVector::getQuestionID)
					.distinct().collect(Collectors.toSet());
			return inputQuestions.stream().filter(question -> mappedQuestionIds.contains(question.getId()))
					.collect(Collectors.toList());
		} else {
			return inputQuestions;
		}
	}
	
	@Override
	public void addQuestionHistory(QuestionHistory history) {
		if (history != null) {
			historyDao.insertQuestionHistory(history);
		}
	}
	
	@Override
	public void deleteHistory(Long historyId) {
		historyDao.deleteQuestionHistory(historyId);
	}
	
	@Override
	public void updateHistory(QuestionHistory history) {
		if (history != null) {
			historyDao.updateQuestionHistory(history);
		}
	}
	
	@Override
	public List<QnAPair> getUserQnAHistory(int userId) {
		List<QnAPair> result = new ArrayList<QnAPair>();
		List<QuestionHistory> histories = historyDao.getHistoryByUserId(userId);
		for (QuestionHistory history : histories) {
			Long questionId = history.getQuestionId();
			QnAPair qnaPair = new QnAPair();
			qnaPair.setQuestion(questionDao.getQuestionContent(questionId));
			qnaPair.setQuestionId(questionId);
			if (history.getReferenceQuestionId() != null) {
				List<Answer> answers = qnaDao.getAnswersByQuestionId(questionId);
				Answer answer = answers.stream().findAny().orElse(new Answer());
				qnaPair.setAnswer(answer.getContent());
				qnaPair.setAnswerId(answer.getId());
			}
			result.add(qnaPair);
		}
		return result;
	}
}
