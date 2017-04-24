package vn.hcmuaf.nlp.ui.screen.login;

import vn.hcmuaf.nlp.main.ManagerScreen;
import vn.hcmuaf.nlp.ui.model.User;
import vn.hcmuaf.nlp.ui.service.LoginService;

public class AdminLoginScreen extends LoginScreen {
	private static final long serialVersionUID = 1L;
	private ManagerScreen managerScreen;
	
	public AdminLoginScreen(LoginService loginService) {
		super(loginService);
	}
	
	public ManagerScreen getManagerScreen() {
		return managerScreen;
	}
	
	public void setManagerScreen(ManagerScreen managerScreen) {
		this.managerScreen = managerScreen;
	}
	
	@Override
	public void addUserPanel(String fullName) {
		managerScreen.addUserInfoPanel(fullName);
	}
	
	@Override
	public boolean logIn(String email, String passWord) throws Exception {
		boolean status = loginService.login(email, passWord);
		if (status) {
			User currentUser = loginService.getCurrentUser();
			if (currentUser.getRole().getRoleName().equals("admin")) {
				addUserPanel(loginService.getCurrentUser().getContact().getFullName());
				managerScreen.selectManagerQuestionTab();
			}else{
				loginService.setCurrentUser(null);
				loginService.setLoginStatus(false);
				return false;
			}
		}
		return status;
	}
}