package vn.hcmuaf.nlp.ui.screen.question.create;

import java.util.Arrays;

import org.vaadin.teemu.ratingstars.RatingStars;

import vn.hcmuaf.nlp.ui.model.QnAPair;
import vn.hcmuaf.nlp.ui.screen.question.ListQuestionTable;
import vn.hcmuaf.nlp.ui.screen.question.QuestionScreenControl;
import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class CreateQuestionTab extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int LEFT_PART_RATIO = 4;
	private static final int RIGHT_PART_RATIO = 1;
	private QuestionScreenControl questionScreenControl;
	private VerticalLayout relateQuestionPanel;
	private TextArea questionTxt;
	private TextArea answerTxt;
	private Button sendQuestionBtn;
	private Button newQuestionBtn;
	private Button sendFeedBackBtn;
	private RatingStars ratingStar;
	private NativeSelect feebBackType;
	private OptionGroup feedBackAction;
	private ListQuestionTable relateQuestionTable;
	private BeanItemContainer<QnAPair> relateQnABeanItems;
	private HorizontalLayout questionPanel;
	private HorizontalLayout answerPanel;
	private HorizontalLayout relatePanel;
	private HorizontalLayout feedBackPanel;
	private CreateQuestionTabControl tabControl;
	
	public CreateQuestionTab(QuestionScreenControl questionScreenControl) {
		this.questionScreenControl = questionScreenControl;
		initComponent();
		tabControl = new CreateQuestionTabControl(this);
		tabControl.initActionHandle();
	}
	
	private void initComponent() {
		initInputQuestionPanel();
		initSubmitPanel();
		initAnswerPanel();
		initRelatePanel();
		initFeedBackPanel();
		hideQnAResultComponent();
	}
	
	private void initSubmitPanel() {
		sendQuestionBtn = new Button(MessageUtils.getString("QuestionScreen.tab.new-question.btn.submit"));
		newQuestionBtn = new Button(MessageUtils.getString("QuestionScreen.tab.new-question.btn.new"));
		newQuestionBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		sendQuestionBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		HorizontalLayout submitQuestionLayout = new HorizontalLayout();
		submitQuestionLayout.setSpacing(true);
		submitQuestionLayout.addStyleName(NLPConstants.STYLE_HORIZONTAL_CUSTOM);
		Label nullLbl = new Label("");
		submitQuestionLayout.addComponent(nullLbl);
		submitQuestionLayout.addComponent(sendQuestionBtn);
		submitQuestionLayout.addComponent(newQuestionBtn);
		submitQuestionLayout.setExpandRatio(nullLbl, RIGHT_PART_RATIO);
		submitQuestionLayout.setExpandRatio(sendQuestionBtn, 4);
		submitQuestionLayout.setExpandRatio(newQuestionBtn, 4);
		newQuestionBtn.setVisible(false);
		submitQuestionLayout.setSizeFull();
		addComponent(submitQuestionLayout);
	}
	
	private void initInputQuestionPanel() {
		questionTxt = new TextArea();
		questionPanel = addLineLayout(questionTxt,
				MessageUtils.getString("QuestionScreen.tab.new-question.label.inputQuestion"));
		addComponent(questionPanel);
	}
	
	private void initAnswerPanel() {
		answerTxt = new TextArea();
		answerPanel = addLineLayout(answerTxt, MessageUtils.getString("QuestionScreen.tab.new-question.label.answer"));
		addComponent(answerPanel);
	}
	
	private void initRelatePanel() {
		relateQuestionPanel = new VerticalLayout();
		relateQnABeanItems = new BeanItemContainer<QnAPair>(QnAPair.class);
		relateQuestionTable = new ListQuestionTable(relateQnABeanItems, true);
		relateQuestionPanel.addComponent(relateQuestionTable);
		relatePanel = addLineLayout(relateQuestionPanel,
				MessageUtils.getString("QuestionScreen.tab.new-question.label.relate"));
		addComponent(relatePanel);
	}
	
	private void initFeedBackPanel() {
		VerticalLayout feedBackFrame = new VerticalLayout();
		feedBackFrame.addStyleName(ValoTheme.LAYOUT_WELL);
		feebBackType = new NativeSelect();
		feebBackType.setNullSelectionAllowed(false);
		feebBackType.addItems(Arrays.asList(NLPConstants.FEEDBACK_TYPES));
		feebBackType.setNullSelectionAllowed(false);
		feebBackType.setValue(NLPConstants.FEEDBACK_TYPES[0]);
		ratingStar = new RatingStars();
		ratingStar.setMaxValue(NLPConstants.RATING_STAR_MAX);
		ratingStar.setValue(NLPConstants.RATING_START_INIT);
		for (int i = 0; i < NLPConstants.RATING_STAR_MAX; i++) {
			ratingStar.setValueCaption(i + 1, NLPConstants.STAR_CAPTIONS[i]);
		}
		HorizontalLayout feedbackTypeLayout = addFeedBackLineLayout(feebBackType,
				MessageUtils.getString("QuestionScreen.tab.new-question.label.feedback"));
		feedBackFrame.addComponent(feedbackTypeLayout);
		HorizontalLayout feedbackRateLayout = addFeedBackLineLayout(ratingStar,
				MessageUtils.getString("QuestionScreen.tab.new-question.label.feedbackScore"));
		feedBackFrame.addComponent(feedbackRateLayout);
		feedBackAction = new OptionGroup();
		feedBackAction.addItems(Arrays.asList(NLPConstants.FEEDBACK_ACTIONS));
		feedBackAction.setMultiSelect(false);
		feedBackAction.setValue(NLPConstants.FEEDBACK_ACTIONS[0]);
		HorizontalLayout feedbackActiontLayout = addFeedBackLineLayout(feedBackAction,
				MessageUtils.getString("QuestionScreen.tab.new-question.label.feedbackAction"));
		feedBackFrame.addComponent(feedbackActiontLayout);
		feedbackActiontLayout.setVisible(false);
		sendFeedBackBtn = new Button(MessageUtils.getString("QuestionScreen.tab.new-question.btn.feedback"));
		HorizontalLayout feedbackSubmitLayout = addFeedBackLineLayout(sendFeedBackBtn, "");
		feedBackFrame.addComponent(feedbackSubmitLayout);
		feebBackType.addValueChangeListener(event -> {
			String selected = event.getProperty().getValue().toString();
			if (!NLPConstants.FEEDBACK_TYPES[0].equals(selected)) {
				feedbackActiontLayout.setVisible(true);
			} else {
				feedbackActiontLayout.setVisible(false);
			}
		});
		feedBackPanel = addLineLayout(feedBackFrame,
				MessageUtils.getString("QuestionScreen.tab.new-question.label.feedback"));
		addComponent(feedBackPanel);
	}
	
	public Label createLabel(String message) {
		Label label = new Label(message);
		label.addStyleName(ValoTheme.LABEL_LIGHT);
		label.addStyleName(NLPConstants.STYLE_LABEL_CUSTOM);
		return label;
	}
	
	public void hideQnAResultComponent() {
		answerPanel.setVisible(false);
		relatePanel.setVisible(false);
		feedBackPanel.setVisible(false);
		questionTxt.setReadOnly(false);
		questionTxt.setValue("");
		relateQnABeanItems.removeAllItems();
		relatePanel.setImmediate(true);
	}
	
	public void showQnAResultComponent() {
		answerPanel.setVisible(true);
		relatePanel.setVisible(true);
		feedBackPanel.setVisible(true);
	}
	
	private HorizontalLayout addFeedBackLineLayout(Component sencondPart, String label) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.addStyleName(NLPConstants.STYLE_HORIZONTAL_CUSTOM);
		Label componentLabel = createLabel(label);
		layout.addComponent(componentLabel);
		layout.addComponent(sencondPart);
		layout.setExpandRatio(componentLabel, 1);
		layout.setExpandRatio(sencondPart, 2);
		layout.setSizeFull();
		return layout;
	}
	
	private HorizontalLayout addLineLayout(Component sencondPart, String label) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.addStyleName(NLPConstants.STYLE_HORIZONTAL_CUSTOM);
		Label componentLabel = createLabel(label);
		layout.addComponent(componentLabel);
		layout.addComponent(sencondPart);
		sencondPart.setSizeFull();
		layout.setExpandRatio(componentLabel, RIGHT_PART_RATIO);
		layout.setExpandRatio(sencondPart, LEFT_PART_RATIO);
		layout.setSizeFull();
		return layout;
	}
	
	public TextArea getQuestionTxt() {
		return questionTxt;
	}
	
	public TextArea getAnswerTxt() {
		return answerTxt;
	}
	
	public Button getSendQuestionBtn() {
		return sendQuestionBtn;
	}
	
	public RatingStars getRatingStar() {
		return ratingStar;
	}
	
	public BeanItemContainer<QnAPair> getRelateQnABeanItems() {
		return relateQnABeanItems;
	}
	
	public NativeSelect getFeebBackType() {
		return feebBackType;
	}
	
	public void setFeebBackType(NativeSelect feebBackType) {
		this.feebBackType = feebBackType;
	}
	
	public Button getSendFeedBackBtn() {
		return sendFeedBackBtn;
	}
	
	public QuestionScreenControl getQuestionScreenControl() {
		return questionScreenControl;
	}
	
	public void setQuestionScreenControl(QuestionScreenControl questionScreenControl) {
		this.questionScreenControl = questionScreenControl;
	}
	
	public Double getQuestionRating() {
		return ratingStar.getValue();
	}
	
	public void setQuestionTxt(TextArea questionTxt) {
		this.questionTxt = questionTxt;
	}
	
	public Button getNewQuestionBtn() {
		return newQuestionBtn;
	}
}
