package vn.hcmuaf.nlp.ui.screen.help;

import vn.hcmuaf.nlp.util.MessageUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class HelpScreen extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	public HelpScreen() {
		Panel introducPanel =createContainPanel(MessageUtils.getString("MainScreen.help.guild.title"));
		Label introductLabel = new Label(buildIntroducText(),ContentMode.HTML);
		introducPanel.setContent(introductLabel);
		addComponent(introducPanel);
		
		Panel userGuildPanel = createContainPanel(MessageUtils.getString("MainScreen.home.feedBack.title"));
		FormLayout userGuildContent = new FormLayout();
		TextArea feedBackTxt =new TextArea(MessageUtils.getString("MainScreen.home.feedBack.content"));
		feedBackTxt.setSizeFull();
		Button sendFeedBack = new Button(MessageUtils.getString("MainScreen.home.feedBack.send"));
		sendFeedBack.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		userGuildContent.addComponent(feedBackTxt);
		userGuildContent.addComponent(sendFeedBack);
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
		content.append("<p>"+MessageUtils.getString("MainScreen.help.guild.intro")+"</p>\n");
		content.append("<p>"+MessageUtils.getString("MainScreen.help.guild.intro.next")+"</p>\n");
		content.append("<p>"+MessageUtils.getString("MainScreen.help.guild.intro.end")+"</p>\n");
		return content.toString();
	}
}
