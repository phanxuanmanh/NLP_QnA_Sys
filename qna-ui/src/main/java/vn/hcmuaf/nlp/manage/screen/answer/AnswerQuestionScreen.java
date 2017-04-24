package vn.hcmuaf.nlp.manage.screen.answer;

import vn.hcmuaf.nlp.ui.service.LoginService;

import com.vaadin.ui.VerticalLayout;

public class AnswerQuestionScreen   extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private AnswerQuestionScreenControl answerQuestionScreenControl;
	private LoginService loginService;
	
	public AnswerQuestionScreen(LoginService loginService){
		this.loginService = loginService;
		this.answerQuestionScreenControl = new AnswerQuestionScreenControl(this);
		answerQuestionScreenControl.initScreen();
	}
	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
}
