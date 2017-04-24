package vn.hcmuaf.nlp.ui.screen.question.history;

import java.util.List;

import vn.hcmuaf.nlp.ui.model.QnAPair;
import vn.hcmuaf.nlp.ui.screen.question.ListQuestionTable;
import vn.hcmuaf.nlp.ui.screen.question.QuestionScreenControl;
import vn.hcmuaf.nlp.ui.service.QnAServiceProvider;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.VerticalLayout;

public class ViewOldQuestionTab extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionScreenControl questionScreenControl;

	public ViewOldQuestionTab(QuestionScreenControl questionScreenControl) {
		this.questionScreenControl = questionScreenControl;
		BeanItemContainer<QnAPair> beanItem = new BeanItemContainer<QnAPair>(QnAPair.class);
		beanItem.addAll(loadTemData());
		ListQuestionTable relateQuestionTable = new ListQuestionTable(beanItem,true);
		addComponent(relateQuestionTable);
	}

	private List<QnAPair> loadTemData() {
		return QnAServiceProvider.getQnAHistoryByUser(questionScreenControl.getCurrentUser().getId());
	}

	public QuestionScreenControl getQuestionScreenControl() {
		return questionScreenControl;
	}

	public void setQuestionScreenControl(QuestionScreenControl questionScreenControl) {
		this.questionScreenControl = questionScreenControl;
	}

}
