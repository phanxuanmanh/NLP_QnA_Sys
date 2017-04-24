package hcmuaf.nlp.core.service.impl;

import hcmuaf.nlp.core.dao.AnswerDao;
import hcmuaf.nlp.core.dao.QnADao;
import hcmuaf.nlp.core.dao.QuestionDao;
import hcmuaf.nlp.core.dao.QuestionHistoryDao;
import hcmuaf.nlp.core.dao.UserDao;
import hcmuaf.nlp.core.model.QnAPairEntity;
import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.model.QuestionHistory;
import hcmuaf.nlp.core.model.UnAnsweredQnA;
import hcmuaf.nlp.core.model.User;
import hcmuaf.nlp.core.service.QnAManageService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("manageService")
public class QnAManageServiceImpl implements QnAManageService {
	@Autowired
	private QuestionHistoryDao questionHistoryDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private AnswerDao answerDao;
	@Autowired
	private QnADao qnaDao;
	@Override
	public Long addAnswer(UnAnsweredQnA qna) {
		if(qna.getQuestion().getId()!=null && StringUtils.isNoneBlank(qna.getAnswer())){
			Long answerId = answerDao.insertAnswer(qna.getAnswer());
			QnAPairEntity qnaEntity = new QnAPairEntity();
			qnaEntity.setAnswer(answerId);
			qnaEntity.setQuestion(Long.valueOf(qna.getQuestion().getId()));
			Question question = qna.getQuestion();
			question.setExpireDate(qna.getExpiredDate());
			questionDao.updateQuestion(question);
			qnaDao.insertQnAPair(qnaEntity);
			QuestionHistory questionHistory = questionHistoryDao.getQuestionHistoryById(qna.getId());
			questionHistory.setAnswerUserId(qna.getAnswerUserId());
			questionHistory.setReferenceQuestionId(qna.getQuestion().getId());
			questionHistoryDao.updateQuestionHistory(questionHistory);
			System.out.println("Answer added "+ answerId);
			return answerId;
		}
		return null;

	}


	@Override
	public List<UnAnsweredQnA> getRecentUnAnswerQuestion(Date from) {
		List<QuestionHistory> listQuestionHistory = questionHistoryDao.getListRecentUnAnswer(from);
		return listQuestionHistory.stream().map(this::convertedToUnAnswerdQnA).collect(Collectors.toList());
	}
	
	private UnAnsweredQnA convertedToUnAnswerdQnA(QuestionHistory questionHistory){
		UnAnsweredQnA uQnA = new UnAnsweredQnA();
		uQnA.setId(questionHistory.getId());
		uQnA.setCreatedDate(questionHistory.getCreatedDate());
		User createdUser =  userDao.getUser(questionHistory.getCreateUserId());
		Question question = questionDao.getQuestionByID(questionHistory.getQuestionId());
		uQnA.setCreateUserName(createdUser.getEmail());
		uQnA.setCreateUserId(createdUser.getId());
		uQnA.setQuestion(question);
		return uQnA;
	}
	@Override
	public List<QuestionHistory> getRecentQnA(Date from, int fetchSize, int firstResult) {
		List<QuestionHistory> listQuestionHistory = questionHistoryDao.getListRecentQnA(from, fetchSize, firstResult);
		return listQuestionHistory;
	}

}
