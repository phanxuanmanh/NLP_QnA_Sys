package vn.hcmuaf.nlp.main;

import javax.servlet.annotation.WebServlet;

import org.vaadin.dialogs.ConfirmDialog;

import vn.hcmuaf.nlp.manage.screen.answer.AnswerQuestionScreen;
import vn.hcmuaf.nlp.manage.screen.question.ManageQuestionScreen;
import vn.hcmuaf.nlp.ui.screen.home.HomeScreen;
import vn.hcmuaf.nlp.ui.screen.login.AdminLoginScreen;
import vn.hcmuaf.nlp.ui.service.LoginService;
import vn.hcmuaf.nlp.ui.service.impl.LoginServiceImpl;
import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme("mytheme")
@Widgetset("vn.hcmuaf.nlp.qna_ui.MyAppWidgetset")
public class ManagerScreen extends UI {

	private static final long serialVersionUID = 1L;
	private HorizontalLayout menuBar;
	private Layout mainContent;
	private CustomLayout mainLayout;
	private HorizontalLayout userInfoPanel;
	private Button manageQuestionBtn;
	private Button answerQuestionBtn;
	private Button homeBtn;
	private LoginService loginService;
	private AdminLoginScreen loginScreen;
	@Override
	protected void init(VaadinRequest request) {
		Page.getCurrent().setTitle(MessageUtils.getString("ManagerScreen.title"));
		loginService = new LoginServiceImpl();
		loginScreen= new AdminLoginScreen(loginService);
		loginScreen.setManagerScreen(this);
		innitComponent();
		mainLayout = new CustomLayout(NLPConstants.MAIN_LAYOUT_NAME);
		mainLayout.addComponent(menuBar, NLPConstants.MAIN_LAYOUT_LOCATION_MENUBAR);
		mainLayout.addComponent(mainContent, NLPConstants.MAIN_LAYOUT_LOCATION_MAIN);
		setContent(mainLayout);

	}

	public void innitComponent() {
		menuBar = new HorizontalLayout();
		mainContent = new VerticalLayout();
		homeBtn = addButton(MessageUtils.getString("MainScreen.menu.home"), new HomeScreen());
		homeBtn.setIcon(FontAwesome.HOME);
		menuBar.addComponent(homeBtn);
		
		manageQuestionBtn = addAdminButton(MessageUtils.getString("ManagerScreen.menu.manage-question"), new ManageQuestionScreen(this.loginService));
		manageQuestionBtn.setIcon(FontAwesome.TASKS);
		menuBar.addComponent(manageQuestionBtn);
		answerQuestionBtn = addAdminButton(MessageUtils.getString("ManagerScreen.menu.answer"),
				new AnswerQuestionScreen(this.loginService));
		answerQuestionBtn.setIcon(FontAwesome.REPLY);
		menuBar.addComponent(answerQuestionBtn);
		Button helpBtn = addButton(MessageUtils.getString("ManagerScreen.menu.take-question"), new VerticalLayout());
		helpBtn.setIcon(FontAwesome.INFO);
		//menuBar.addComponent(helpBtn);

	}

	public void addUserInfoPanel(String name) {
		userInfoPanel = new HorizontalLayout();
		Label welcomeLbl = new Label(MessageUtils.getString("MainScreen.login.welcome.label"));
		welcomeLbl.addStyleName("align-right");
		userInfoPanel.addComponent(welcomeLbl);
		MenuBar UserInfoBar = createUserMenuBar(name);
		userInfoPanel.addComponent(UserInfoBar);
		userInfoPanel.setComponentAlignment(UserInfoBar, Alignment.TOP_RIGHT);
		userInfoPanel.setComponentAlignment(welcomeLbl, Alignment.TOP_RIGHT);
		// welcomeLbl.setWidth(50, Unit.PERCENTAGE);
		menuBar.addComponent(userInfoPanel);
		UserInfoBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		menuBar.setComponentAlignment(userInfoPanel, Alignment.TOP_RIGHT);
		userInfoPanel.setWidth(400, Unit.PIXELS);
	}

	public void removeUserInfoPanel() {
		menuBar.removeComponent(userInfoPanel);
		userInfoPanel.removeAllComponents();
	}
	
	public void showLoginNotification() {
		ConfirmDialog.Listener confirmLoginListener = new ConfirmDialog.Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(ConfirmDialog confirmLogin) {
				if (confirmLogin.isConfirmed()) {
					loginScreen.initScreen();
					UI.getCurrent().addWindow(loginScreen);
				}

			}
		};

		ConfirmDialog.show(UI.getCurrent(), MessageUtils.getString("MainScreen.login.request.caption"),
				MessageUtils.getString("MainScreen.login.request.desc"), MessageUtils.getString("MainScreen.login.request.ok"), MessageUtils.getString("MainScreen.login.request.cancel"), confirmLoginListener);
	}
	
	private MenuBar createUserMenuBar(String name) {
		MenuBar userMenuBar = new MenuBar();
		MenuItem main = userMenuBar.addItem(name, null);
		main.addItem(MessageUtils.getString("MainScreen.login.logout.btn"), new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				loginScreen.setLoginStatus(false);
				removeUserInfoPanel();
				homeBtn.click();
			}
		});
		return userMenuBar;
	}

	public Button addButton(String caption, Layout layout) {
		Button button = new Button(caption);
		button.addStyleName(NLPConstants.QNA_MENU);
		button.addClickListener(event -> {
			mainContent.removeAllComponents();
			mainContent.addComponent(layout);
		});
		return button;
	}
	
	private Button addAdminButton(String caption, Layout layout) {
		Button button = new Button(caption);
		button.addStyleName(NLPConstants.QNA_MENU);
		button.addClickListener(event -> {
			mainContent.removeAllComponents();
			boolean loginStatus = loginScreen.getLoginStatus();
			if (loginStatus) {
				mainContent.addComponent(layout);
			} else {
				showLoginNotification();
			}
		});
		return button;
	}
	
	@WebServlet(urlPatterns = "/manage/*", name = "ManagerScreenServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = ManagerScreen.class, productionMode = false)
	public static class ManagerUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}

	public void selectManagerQuestionTab() {
		manageQuestionBtn.click();
	}
}
