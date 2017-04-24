package vn.hcmuaf.nlp.main;

import javax.servlet.annotation.WebServlet;

import org.vaadin.dialogs.ConfirmDialog;

import vn.hcmuaf.nlp.ui.screen.help.HelpScreen;
import vn.hcmuaf.nlp.ui.screen.home.HomeScreen;
import vn.hcmuaf.nlp.ui.screen.login.StudentLoginScreen;
import vn.hcmuaf.nlp.ui.screen.question.QuestionScreen;
import vn.hcmuaf.nlp.ui.screen.question.common.CommonQuestionScreen;
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
public class MainScreen extends UI {

	private static final long serialVersionUID = 1L;
	private HorizontalLayout menuBar;
	private Layout mainContent;
	private StudentLoginScreen loginScreen;

	private CustomLayout mainLayout;
	private HorizontalLayout userInfoPanel;
	private Button makeQuestionBtn;
	private Button homeBtn;
	private LoginService loginService;

	@Override
	protected void init(VaadinRequest request) {
		Page.getCurrent().setTitle(MessageUtils.getString("MainScreen.title"));
		loginService = new LoginServiceImpl();
		loginScreen = new StudentLoginScreen(loginService);
		loginScreen.setMainScreen(this);
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
		makeQuestionBtn = addMakeQUestionButton(MessageUtils.getString("MainScreen.menu.take-question"));
		makeQuestionBtn.setIcon(FontAwesome.QUESTION);
		menuBar.addComponent(makeQuestionBtn);
		Button commonQuestionBtn = addButton(MessageUtils.getString("MainScreen.menu.common-question"),
				new CommonQuestionScreen());
		commonQuestionBtn.setIcon(FontAwesome.HISTORY);
		menuBar.addComponent(commonQuestionBtn);
		Button helpBtn = addButton(MessageUtils.getString("MainScreen.menu.help"), new HelpScreen());
		helpBtn.setIcon(FontAwesome.HAND_O_UP);
		menuBar.addComponent(helpBtn);

	}

	public void addUserInfoPanel(String name) {
		userInfoPanel = new HorizontalLayout();
		Label welcomeLbl = new Label(MessageUtils.getString("MainScreen.login.welcome.label"));
		welcomeLbl.addStyleName("align-right");
		userInfoPanel.addComponent(welcomeLbl);
		MenuBar UserInfoBar = getUserMenuBar(name);
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

	private MenuBar getUserMenuBar(String name) {
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

	public Button addMakeQUestionButton(String caption) {
		Button button = new Button(caption);
		button.addStyleName(NLPConstants.QNA_MENU);
		button.addClickListener(event -> {
			mainContent.removeAllComponents();
			boolean loginStatus = loginScreen.getLoginStatus();
			if (loginStatus) {
				mainContent.addComponent(new QuestionScreen(loginService));
			} else {
				showLoginNotification();
			}
		});
		return button;
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

	public void selectMakeQuestionTab() {
		makeQuestionBtn.click();
	}

	@WebServlet(urlPatterns = "/ui/*", name = "MainScreenServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainScreen.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}
}
