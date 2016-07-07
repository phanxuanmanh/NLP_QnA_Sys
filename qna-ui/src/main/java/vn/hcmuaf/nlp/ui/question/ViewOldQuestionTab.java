package vn.hcmuaf.nlp.ui.question;

import java.util.ArrayList;
import java.util.List;

import vn.hcmuaf.nlp.ui.model.QnAPairModel;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.VerticalLayout;

public class ViewOldQuestionTab extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionScreen questionScreen;

	public ViewOldQuestionTab(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
		BeanItemContainer<QnAPairModel> beanItem = new BeanItemContainer<QnAPairModel>(
				QnAPairModel.class);
		beanItem.addAll(loadTemData());
		ListQuestionTable relateQuestionTable = new ListQuestionTable(beanItem);
		addComponent(relateQuestionTable);
	}

	private List<QnAPairModel> loadTemData() {
		List<QnAPairModel> listQuestion = new ArrayList<QnAPairModel>();
		QnAPairModel pair = new QnAPairModel();
		pair.setQuestionId(1);
		pair.setAnswerId(1);
		pair.setQuestionContent("Khi nao co diem vay thay");
		pair.setAnswerContent("Thang 8 nha em");
		pair.setTypeId(1);
		listQuestion.add(pair);
		return listQuestion;
	}
}
