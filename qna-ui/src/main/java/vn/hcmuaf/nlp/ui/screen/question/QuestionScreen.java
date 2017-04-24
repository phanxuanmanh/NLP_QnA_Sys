package vn.hcmuaf.nlp.ui.screen.question;



import vn.hcmuaf.nlp.ui.service.LoginService;

import com.vaadin.ui.HorizontalLayout;

public class QuestionScreen extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionScreenControl screenControl;

	public QuestionScreen(LoginService loginService) {
		setSizeFull();
		screenControl = new QuestionScreenControl(loginService, this);

	}

	public QuestionScreenControl getScreenControl() {
		return screenControl;
	}


}
