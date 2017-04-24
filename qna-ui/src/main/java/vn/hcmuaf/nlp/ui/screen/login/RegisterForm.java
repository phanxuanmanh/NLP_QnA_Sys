package vn.hcmuaf.nlp.ui.screen.login;

import vn.hcmuaf.nlp.ui.model.Contact;
import vn.hcmuaf.nlp.ui.model.User;
import vn.hcmuaf.nlp.ui.service.LoginService;
import vn.hcmuaf.nlp.ui.service.impl.LoginServiceImpl;
import vn.hcmuaf.nlp.util.MessageUtils;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class RegisterForm extends FormLayout {
	private static final String SPACE_BETWEEN_NAME = " ";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginScreen loginScreen;
	private TextField email;
	private PasswordField passwordTxt;
	private PasswordField passwordConfirmTxt;
	private TextField firstName;
	private TextField lastName;
	private TextField phone;
	private TextField address;
	private Button register;

	public RegisterForm(LoginScreen loginScreen) {
		this.loginScreen = loginScreen;
		addStyleName(ValoTheme.PANEL_WELL);
		setMargin(true);
		initForm();
		handleRegisterAction();

	}

	private void handleRegisterAction() {
		register.addClickListener(e -> {
			if (isValidForm()) {
				User user = new User();
				user.setEmail(email.getValue());
				user.setPassWord(passwordTxt.getValue());
				Contact contact = new Contact();
				contact.setFullName(firstName.getValue() + SPACE_BETWEEN_NAME + lastName.getValue());
				contact.setPhone(phone.getValue());
				contact.setAddress(address.getValue());
				user.setContact(contact);
				LoginService loginService = new LoginServiceImpl();
				loginService.addUser(user);
				loginScreen.initScreen();
			}
		});

	}

	private void initForm() {
		setMargin(true);
		email = new TextField(MessageUtils.getString("MainScreen.register.email.label"));
		email.setSizeFull();
		email.addValidator(new EmailValidator(MessageUtils.getString("MainScreen.register.email.validate")));

		passwordTxt = new PasswordField(MessageUtils.getString("MainScreen.register.password.label"));
		passwordTxt.setSizeFull();
		passwordTxt.addValidator(new StringLengthValidator(MessageUtils
				.getString("MainScreen.register.password.len.validate"), 6, 20, false));
		passwordTxt.setValidationVisible(false);
		passwordTxt.addFocusListener(e->{
			passwordTxt.setValidationVisible(true);
		});
		
		passwordConfirmTxt = new PasswordField(MessageUtils.getString("MainScreen.register.password.confirm.label"));
		passwordConfirmTxt.setSizeFull();
		passwordConfirmTxt.addValidator(new StringLengthValidator(MessageUtils
				.getString("MainScreen.register.password.len.validate"), 6, 20, false));
		passwordConfirmTxt.setValidationVisible(false);
		passwordConfirmTxt.addFocusListener(e->{
			passwordConfirmTxt.setValidationVisible(true);
		});

		firstName = new TextField(MessageUtils.getString("MainScreen.register.firstname.label"));
		firstName.setSizeFull();
		firstName.addValidator(new StringLengthValidator(MessageUtils.getString("MainScreen.register.null.validate"),
				1, 100,false));
		firstName.setValidationVisible(false);
		firstName.addFocusListener(e->{
			firstName.setValidationVisible(true);
		});

		lastName = new TextField(MessageUtils.getString("MainScreen.register.lastname.label"));
		lastName.setSizeFull();
		lastName.addValidator(new StringLengthValidator(MessageUtils.getString("MainScreen.register.null.validate"), 1,
				100, false));
		lastName.setValidationVisible(false);
		lastName.addFocusListener(e->{
			lastName.setValidationVisible(true);
		});

		phone = new TextField(MessageUtils.getString("MainScreen.register.phone.label"));
		phone.setSizeFull();
		phone.addValidator(new StringLengthValidator(MessageUtils.getString("MainScreen.register.null.validate"), 1,
				100, false));
		phone.setValidationVisible(false);
		phone.addFocusListener(e->{
			phone.setValidationVisible(true);
		});

		address = new TextField(MessageUtils.getString("MainScreen.register.address.label"));
		address.setSizeFull();

		register = new Button(MessageUtils.getString("MainScreen.login.register.btn"));
		register.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		addComponent(email);
		addComponent(firstName);
		addComponent(lastName);
		addComponent(passwordTxt);
		addComponent(passwordConfirmTxt);
		addComponent(phone);
		addComponent(address);
		addComponent(register);

		
	}

	public boolean isValidForm() {
		if ((!email.isValid()) || (!passwordTxt.isValid()) || (!passwordConfirmTxt.isValid()) || (!firstName.isValid())
				|| (!lastName.isValid()) || (!phone.isValid())) {
			Notification.show(MessageUtils.getString("MainScreen.register.validate.message"),
					Notification.Type.WARNING_MESSAGE);
			return false;
		}
		if (!(passwordTxt.getValue().equals(passwordConfirmTxt.getValue()))) {
			Notification.show(MessageUtils.getString("MainScreen.register.password.confirm.validate"),
					Notification.Type.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
}
