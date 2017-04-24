/**
 * @author Manh Phan
 *
 * Edited date Aug 15, 2016
 */
package vn.hcmuaf.nlp.manage.service;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import vn.hcmuaf.nlp.manage.service.impl.QuestionHistoryServiceImpl;
import vn.hcmuaf.nlp.ui.model.Answer;
import vn.hcmuaf.nlp.ui.model.QuestionHistory;
import vn.hcmuaf.nlp.ui.model.UnAnsweredQnA;
import vn.hcmuaf.nlp.ui.model.User;
import vn.hcmuaf.nlp.ui.service.LoginService;
import vn.hcmuaf.nlp.ui.service.impl.LoginServiceImpl;

/**
 * The Class QuestionHistoryServiceProvider.
 */
public class QuestionHistoryServiceProvider {

	/** The service. */
	public static QuestionHistoryService service = new QuestionHistoryServiceImpl();

	/**
	 * Gets the list recent question history.
	 *
	 * @param from
	 *            the from
	 * @param size
	 *            the size
	 * @param firstResult
	 *            the first result
	 * @return the list recent question history
	 */
	public static List<QuestionHistory> getListRecentQuestionHistory(Date from, int size, int firstResult) {
		try {
			return service.getListRecentQuestionHistory(from, size, firstResult);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the list un answer question.
	 *
	 * @param from
	 *            the from
	 * @param size
	 *            the size
	 * @param firstResult
	 *            the first result
	 * @return the list un-answer question
	 */
	public static List<UnAnsweredQnA> getListUnAnswerQuestion(Date from) {
		try {
			return service.getListUnAnswerQuestion(from);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getAnswerByQuestionId(Integer questionId) {
		try {
			Answer answer = service.getAnswerByQuestionId(questionId);
			if(answer!=null){
			return answer.getContent();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getQuestionByQuestionId(int questionId) {
		try {
			return service.getContentByQuestionId(questionId).getContent();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getUserEmailById(int userID) {
		LoginService loginService = new LoginServiceImpl();
		User user;
		try {
			user = loginService.getUserById(userID);
			return user.getEmail();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteHistory(int historyId) {
		try {
			service.deleteQuestionHistory(historyId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	public static void addAnswer(UnAnsweredQnA qna) {
		try {
			service.answerQuestion(qna);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
