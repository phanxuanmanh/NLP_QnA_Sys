package vn.hcmuaf.nlp.ui.main;

import javax.servlet.annotation.WebServlet;

import vn.hcmuaf.nlp.ui.home.HomeScreen;
import vn.hcmuaf.nlp.ui.question.QuestionScreen;
import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@Widgetset("vn.hcmuaf.nlp.qna_ui.MyAppWidgetset")
public class MainScreen extends UI {

	private static final long serialVersionUID = 1L;
	private HorizontalLayout menuBar;
	private Layout mainContent;

	private CustomLayout mainLayout;

	@Override
	protected void init(VaadinRequest request) {
		innitComponent();
		mainLayout = new CustomLayout(NLPConstants.MAIN_LAYOUT_NAME);
		mainLayout.addComponent(menuBar,
				NLPConstants.MAIN_LAYOUT_LOCATION_MENUBAR);
		mainLayout.addComponent(mainContent,
				NLPConstants.MAIN_LAYOUT_LOCATION_MAIN);
		setContent(mainLayout);

	}

	public void innitComponent() {
		menuBar = new HorizontalLayout();
		mainContent = new VerticalLayout();
		menuBar.addComponent(addButton(
				MessageUtils.getString("MainScreen.menu.home"),
				new HomeScreen()));
		menuBar.addComponent(addButton(
				MessageUtils.getString("MainScreen.menu.take-question"),
				new QuestionScreen()));
		menuBar.addComponent(addButton(
				MessageUtils.getString("MainScreen.menu.common-question"),
				new VerticalLayout()));
		menuBar.addComponent(addButton(
				MessageUtils.getString("MainScreen.menu.help"),
				new VerticalLayout()));
	}

	public Button addButton(String caption, Layout layout) {
		Button button = new Button(caption);
		button.addStyleName(NLPConstants.QNA_MENU);
		button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				mainContent.removeAllComponents();
				mainContent.addComponent(layout);
				Notification.show(getCaption());
			}
		});
		return button;
	}

	@WebServlet(urlPatterns = "/*", name = "MainScreenServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainScreen.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}
}
