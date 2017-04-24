package vn.hcmuaf.nlp.ui.screen.login;

import vn.hcmuaf.nlp.ui.service.LoginService;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LoginScreen extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LoginService loginService;
	public VerticalLayout mainContent;
	public LoginForm loginForm;

	public VerticalLayout getMainContent() {
		return mainContent;
	}

	public void setMainContent(VerticalLayout mainContent) {
		this.mainContent = mainContent;
	}

	public LoginScreen(LoginService loginService) {
		this.loginService = loginService;
		initScreen();
	}

	public void initScreen() {
		mainContent = new VerticalLayout();
		mainContent.removeAllComponents();
		loginForm = new LoginForm(this);
		mainContent.addComponent(loginForm);
		setContent(mainContent);
		setModal(true);
		setWidth(30f, Unit.PERCENTAGE);
		setResizable(false);
		center();
	}

	public boolean logIn(String email, String passWord) throws Exception {
		boolean status = loginService.login(email, passWord);
		if (status) {
			addUserPanel(loginService.getCurrentUser().getContact().getFullName());
		}
		return status;

	}

	public boolean getLoginStatus() {
		return loginService.getLoginStatus();
	}

	public void setLoginStatus(boolean status) {
		loginService.setLoginStatus(status);
	}

	public void addUserPanel(String fullName) {
	}




}
