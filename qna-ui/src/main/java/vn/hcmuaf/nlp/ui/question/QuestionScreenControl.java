/**
 * @author Manh Phan
 *
 * Edited date Jun 27, 2016
 */
package vn.hcmuaf.nlp.ui.question;

import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

public class QuestionScreenControl {
	private QuestionScreen questionScreen;
	private VerticalLayout controlPanel;
	private Panel mainScreenPanel;
	private MakeQuestionTab makeQuestionTab;
	private ViewOldQuestionTab viewOldQuestionTab;
	private Button newQuestionBtn;
	private Button oldQuestionBtn;

	public QuestionScreenControl(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
		initScreen();
		initTabSelectAction();
	}

	public void initScreen() {
		controlPanel = new VerticalLayout();
		mainScreenPanel = new Panel();
		mainScreenPanel.addStyleName(ValoTheme.PANEL_WELL);
		mainScreenPanel.addStyleName(NLPConstants.STYLE_QUESTION_PANEL_CUSTOM);
		makeQuestionTab = new MakeQuestionTab(questionScreen);
		viewOldQuestionTab = new ViewOldQuestionTab(questionScreen);
		questionScreen.addComponent(controlPanel);
		questionScreen.addComponent(mainScreenPanel);
		questionScreen.setExpandRatio(controlPanel, 1);
		questionScreen.setExpandRatio(mainScreenPanel, 5);
		newQuestionBtn = new Button(
				MessageUtils.getString("QuestionScreen.control.new-question"));
		oldQuestionBtn = new Button(
				MessageUtils.getString("QuestionScreen.control.old-question"));
		newQuestionBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		newQuestionBtn.addStyleName(NLPConstants.BTN_ALIGN_LEFT);
		newQuestionBtn.setIcon(FontAwesome.PENCIL);
		oldQuestionBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		oldQuestionBtn.addStyleName(NLPConstants.BTN_ALIGN_LEFT);
		oldQuestionBtn.setIcon(FontAwesome.EYE);
		newQuestionBtn.setWidth(100f, Unit.PERCENTAGE);
		oldQuestionBtn.setWidth(100f, Unit.PERCENTAGE);
		controlPanel.addComponent(newQuestionBtn);
		controlPanel.addComponent(oldQuestionBtn);
		mainScreenPanel.setHeightUndefined();
		mainScreenPanel.setContent(makeQuestionTab);
	}

	private void initTabSelectAction() {
		newQuestionBtn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				mainScreenPanel.setContent(makeQuestionTab);
			}
		});
		oldQuestionBtn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				mainScreenPanel.setContent(viewOldQuestionTab);
			}
		});
	}

}
