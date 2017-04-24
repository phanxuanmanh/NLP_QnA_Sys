package vn.hcmuaf.nlp.ui.screen.question.common;

import com.vaadin.ui.VerticalLayout;

public class CommonQuestionScreen extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommonQuestionScreenControl screenControl;

	public CommonQuestionScreen() {
		screenControl = new CommonQuestionScreenControl(this);
	}

	public CommonQuestionScreenControl getScreenControl() {
		return screenControl;
	}

}
