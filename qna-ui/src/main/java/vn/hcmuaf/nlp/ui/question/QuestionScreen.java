package vn.hcmuaf.nlp.ui.question;

import com.vaadin.ui.HorizontalLayout;

public class QuestionScreen extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionScreenControl screenControl;
	private QuestionScreenDataControl dataControl;

	public QuestionScreen() {
		setSizeFull();
		screenControl = new QuestionScreenControl(this);
		dataControl = new QuestionScreenDataControl();
	}

	public QuestionScreenControl getScreenControl() {
		return screenControl;
	}

	public QuestionScreenDataControl getDataControl() {
		return dataControl;
	}

}
