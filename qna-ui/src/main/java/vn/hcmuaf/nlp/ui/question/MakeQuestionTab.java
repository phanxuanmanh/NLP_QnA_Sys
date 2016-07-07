package vn.hcmuaf.nlp.ui.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.vaadin.teemu.ratingstars.RatingStars;

import vn.hcmuaf.nlp.ui.model.QnAPairModel;
import vn.hcmuaf.nlp.ui.service.GetAnswerService;
import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MakeQuestionTab extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int LEFT_PART_RATIO = 4;
	private static final int RIGHT_PART_RATIO = 1;
	private QuestionScreen questionScreen;
	private VerticalLayout relateQuestionPanel;
	private TextArea questionTxt;
	private TextArea answerTxt;
	private Button sendQuestion;
	private VerticalLayout feedBackPanel;
	private RatingStars ratingStar;
	private ListQuestionTable relateQuestionTable;
	private BeanItemContainer<QnAPairModel> beanItem;

	public MakeQuestionTab(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
		initComponent();
	}

	private void initComponent() {
		initInputQuestionPanel();
		initSubmitButton();
		initAnswerPanel();
		initRelatePanel();
		initFeedBackPanel();
	}

	private void initAnswerPanel() {
		answerTxt = new TextArea();
		answerTxt.setEnabled(false);
		addComponent(addLineLayout(answerTxt,
				"QuestionScreen.tab.new-question.label.answer"));
	}

	private void initSubmitButton() {
		sendQuestion = new Button(
				MessageUtils
						.getString("QuestionScreen.tab.new-question.btn.submit"));
		HorizontalLayout submitQuestionLayout = new HorizontalLayout();
		submitQuestionLayout.addStyleName(NLPConstants.STYLE_HORIZONTAL_CUSTOM);
		Label nullLbl = new Label("");
		submitQuestionLayout.addComponent(nullLbl);
		submitQuestionLayout.addComponent(sendQuestion);
		submitQuestionLayout.setExpandRatio(nullLbl, RIGHT_PART_RATIO);
		submitQuestionLayout.setExpandRatio(sendQuestion, LEFT_PART_RATIO);
		submitQuestionLayout.setSizeFull();
		addComponent(submitQuestionLayout);
		sendQuestion.addClickListener(event -> {
			List<QnAPairModel> qnaPairs = GetAnswerService
					.getListRelateQuestion(questionTxt.getValue());
			if (qnaPairs != null) {
				QnAPairModel pair = qnaPairs.get(0);
				answerTxt.setValue(pair.getAnswerContent());
				setListQuestionBeanItem(qnaPairs);
			}
		});
	}

	private void initInputQuestionPanel() {
		questionTxt = new TextArea();
		addComponent(addLineLayout(questionTxt,
				"QuestionScreen.tab.new-question.label.inputQuestion"));
	}

	private HorizontalLayout addLineLayout(Component sencondPart,
			String labelPath) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.addStyleName(NLPConstants.STYLE_HORIZONTAL_CUSTOM);
		Label relateQuestionLbl = createLabel(labelPath);
		layout.addComponent(relateQuestionLbl);
		layout.addComponent(sencondPart);
		sencondPart.setSizeFull();
		layout.setExpandRatio(relateQuestionLbl, RIGHT_PART_RATIO);
		layout.setExpandRatio(sencondPart, LEFT_PART_RATIO);
		layout.setSizeFull();
		return layout;
	}

	private void initRelatePanel() {
		relateQuestionPanel = new VerticalLayout();
		beanItem = new BeanItemContainer<QnAPairModel>(QnAPairModel.class);
		beanItem.addAll(loadTemData());
		relateQuestionTable = new ListQuestionTable(beanItem);
		relateQuestionPanel.addComponent(relateQuestionTable);
		addComponent(addLineLayout(relateQuestionPanel,
				"QuestionScreen.tab.new-question.label.relate"));
	}

	private List<QnAPairModel> loadTemData() {
		List<QnAPairModel> listQuestion = new ArrayList<QnAPairModel>();
		QnAPairModel pair = new QnAPairModel();
		pair.setQuestionId(1);
		pair.setAnswerId(1);
		pair.setQuestionContent("Khi nao co diem vay thay");
		pair.setAnswerContent("Thang 8 nha em");
		pair.setTypeId(1);
		listQuestion.add(pair);
		return listQuestion;
	}

	private void initFeedBackPanel() {
		feedBackPanel = new VerticalLayout();
		feedBackPanel.addStyleName(ValoTheme.LAYOUT_WELL);
		addComponent(addLineLayout(feedBackPanel,
				"QuestionScreen.tab.new-question.label.feedback"));

		HorizontalLayout feedbackTypeLayout = new HorizontalLayout();
		HorizontalLayout feedbackRateLayout = new HorizontalLayout();
		feedbackTypeLayout.addStyleName(NLPConstants.STYLE_HORIZONTAL_CUSTOM);
		feedbackRateLayout.addStyleName(NLPConstants.STYLE_HORIZONTAL_CUSTOM);

		feedBackPanel.addComponent(feedbackTypeLayout);
		feedBackPanel.addComponent(feedbackRateLayout);
		feedbackTypeLayout.setSizeFull();
		feedbackRateLayout.setSizeFull();

		Label feedBackTypeLbl = createLabel("QuestionScreen.tab.new-question.label.feedbackType");
		Label feedBackScoreLbl = createLabel("QuestionScreen.tab.new-question.label.feedbackScore");
		feedBackScoreLbl.addStyleName(ValoTheme.LABEL_LIGHT);
		feedBackScoreLbl.addStyleName(NLPConstants.STYLE_LABEL_CUSTOM);
		NativeSelect typeSelect = new NativeSelect();
		typeSelect.setNullSelectionAllowed(false);
		typeSelect.addItems(Arrays.asList(NLPConstants.FEEDBACK_TYPES));
		typeSelect.setNullSelectionAllowed(false);
		typeSelect.setValue(NLPConstants.FEEDBACK_TYPES[0]);

		feedbackTypeLayout.addComponent(feedBackTypeLbl);
		feedbackTypeLayout.addComponent(typeSelect);

		ratingStar = new RatingStars();
		ratingStar.setMaxValue(NLPConstants.RATING_STAR_MAX);
		ratingStar.setValue(NLPConstants.RATING_START_INIT);
		for (int i = 0; i < NLPConstants.RATING_STAR_MAX; i++) {
			ratingStar.setValueCaption(i + 1, NLPConstants.STAR_CAPTION[i]);
		}
		feedbackRateLayout.addComponent(feedBackScoreLbl);
		feedbackRateLayout.addComponent(ratingStar);

	}

	public Label createLabel(String messageLocation) {
		Label label = new Label(MessageUtils.getString(messageLocation));
		label.addStyleName(ValoTheme.LABEL_LIGHT);
		label.addStyleName(NLPConstants.STYLE_LABEL_CUSTOM);
		return label;
	}

	public void setListQuestionBeanItem(List<QnAPairModel> listQnA) {
		beanItem.removeAllItems();
		beanItem.addAll(listQnA);
	}
}
