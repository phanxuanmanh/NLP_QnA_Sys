package vn.hcmuaf.nlp.manage.screen.question;

import vn.hcmuaf.nlp.ui.service.LoginService;

import com.vaadin.ui.VerticalLayout;

public class ManageQuestionScreen extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ManageQuestionScreenControl screenControl;
	private LoginService loginService;

	public ManageQuestionScreen(LoginService loginService) {
		this.loginService = loginService;
		screenControl = new ManageQuestionScreenControl(this);
		screenControl.initScreen();
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
}
