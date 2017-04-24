package vn.hcmuaf.nlp.ui.screen.login;

import vn.hcmuaf.nlp.main.MainScreen;
import vn.hcmuaf.nlp.ui.service.LoginService;

public class StudentLoginScreen extends LoginScreen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainScreen mainScreen;
	
	public StudentLoginScreen(LoginService loginService) {
		super(loginService);
	}
	
	public MainScreen getMainScreen() {
		return mainScreen;
	}
	
	public void setMainScreen(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void addUserPanel(String fullName) {
		mainScreen.addUserInfoPanel(fullName);
	}
	
	@Override
	public boolean logIn(String email, String passWord) throws Exception {
		boolean status = loginService.login(email, passWord);
		if (status) {
			addUserPanel(loginService.getCurrentUser().getContact().getFullName());
			mainScreen.selectMakeQuestionTab();
		}
		return status;
	}
}
