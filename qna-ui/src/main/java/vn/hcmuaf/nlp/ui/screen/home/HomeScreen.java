package vn.hcmuaf.nlp.ui.screen.home;

import vn.hcmuaf.nlp.util.MessageUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class HomeScreen extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	public HomeScreen() {
		Panel introducPanel =createContainPanel(MessageUtils.getString("MainScreen.home.introduction.title"));
		Label introductLabel = new Label(buildIntroducText(),ContentMode.HTML);
		introducPanel.setContent(introductLabel);
		addComponent(introducPanel);
		
		Panel userGuildPanel = createContainPanel(MessageUtils.getString("MainScreen.home.guild.title"));
		VerticalLayout userGuildContent = new VerticalLayout();
		
		Image loginImage = new Image(MessageUtils.getString("MainScreen.home.guild.login.caption"));
		loginImage.setSource(new ThemeResource("../mytheme/images/guild/login.PNG"));
		userGuildContent.addComponent(loginImage);
		
		Image addQuestion = new Image(MessageUtils.getString("MainScreen.home.guild.addQuestin.caption"));
		addQuestion.setSource(new ThemeResource("../mytheme/images/guild/add-question.PNG"));
		userGuildContent.addComponent(addQuestion);
		
		userGuildPanel.setContent(userGuildContent);
		addComponent(userGuildPanel);
	}

	private Panel createContainPanel(String caption){
		Panel panel = new Panel(caption);
		panel.addStyleName("green-background");
		panel.setIcon(FontAwesome.INFO_CIRCLE);
		return panel;
	}
	private String buildIntroducText(){
		StringBuilder content = new StringBuilder();
		content.append("<p>"+MessageUtils.getString("MainScreen.home.introduction.first")+"</p>\n");
		content.append("<p>"+MessageUtils.getString("MainScreen.home.introduction.aim")+"</p>\n");
		content.append("<p>"+MessageUtils.getString("MainScreen.home.introduction.thanks")+"</p>\n");
		return content.toString();
	}
}
