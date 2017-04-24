package vn.hcmuaf.nlp.ui.screen.abtract;

import vn.hcmuaf.nlp.ui.screen.login.LoginScreen;
import vn.hcmuaf.nlp.ui.screen.login.RegisterForm;
import vn.hcmuaf.nlp.util.MessageUtils;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class AbtractLoginForm extends FormLayout {
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public LoginScreen loginScreen;
	public TextField emailTxt;
	public PasswordField passwordTxt;
	public HorizontalLayout buttonPanel;
	public Button login;
	public Button register;

	public AbtractLoginForm(LoginScreen loginScreen) {
		this.loginScreen = loginScreen;
		addStyleName(ValoTheme.PANEL_WELL);
		initForm();
		handleAction();

	}

	public void handleAction() {
		login.addClickListener(e -> {
			String email = emailTxt.getValue();
			String password = passwordTxt.getValue();
			try {
				boolean status = loginScreen.logIn(email, password);
				if (status) {
					System.out.println(status);
					loginScreen.close();
				} else {
					Notification.show(MessageUtils.getString("MainScreen.login.fail.message"),
							Notification.Type.WARNING_MESSAGE);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		register.addClickListener(e -> {
			RegisterForm registerForm = new RegisterForm(loginScreen);
			loginScreen.setContent(registerForm);
		});

	}

	public void initForm() {

		setMargin(true);
		emailTxt = new TextField(MessageUtils.getString("MainScreen.login.user.label"));
		emailTxt.setSizeFull();
		emailTxt.addValidator(new EmailValidator(MessageUtils.getString("MainScreen.register.email.validate")));

		passwordTxt = new PasswordField(MessageUtils.getString("MainScreen.login.password.label"));
		passwordTxt.setSizeFull();
		passwordTxt.addValidator(new StringLengthValidator(MessageUtils
				.getString("MainScreen.register.password.len.validate"), 6, 20, false));
		passwordTxt.setValidationVisible(false);
		passwordTxt.addFocusListener(e -> {
			passwordTxt.setValidationVisible(true);
		});

		buttonPanel = new HorizontalLayout();
		login = new Button(MessageUtils.getString("MainScreen.login.login.btn"));
		login.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		register = new Button(MessageUtils.getString("MainScreen.login.register.btn"));
		register.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		buttonPanel.addComponent(login);
		buttonPanel.addComponent(register);
		buttonPanel.setSpacing(true);
		addComponent(emailTxt);
		addComponent(passwordTxt);
		addComponent(buttonPanel);

	}
}