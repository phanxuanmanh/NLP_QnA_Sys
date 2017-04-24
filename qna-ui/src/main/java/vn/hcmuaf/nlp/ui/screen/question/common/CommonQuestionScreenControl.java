package vn.hcmuaf.nlp.ui.screen.question.common;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import vn.hcmuaf.nlp.ui.model.CommonQuestionSearchCriteria;
import vn.hcmuaf.nlp.ui.model.QnAPair;
import vn.hcmuaf.nlp.ui.screen.question.ListQuestionTable;
import vn.hcmuaf.nlp.ui.service.QnAServiceProvider;
import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CommonQuestionScreenControl {
	private static final String LIST_QNA_PANEL_SIZE = "500px";
	private CommonQuestionScreen mainScreen;
	private Button searchBtn;
	private BeanItemContainer<QnAPair> beanItem;
	private TextField keyWordTxt;
	private NativeSelect fetchSizeSelect;
	private NativeSelect topicSelect;
	private PopupDateField questionsStartDate;

	public CommonQuestionScreenControl(CommonQuestionScreen mainScreen) {
		this.mainScreen = mainScreen;
		initMainScreen();
		initActionHandler();
	}

	private void initActionHandler() {
		searchBtn.addClickListener(e -> {
			Date startDate=questionsStartDate.getValue();
			Long fetchSize = (Long)fetchSizeSelect.getValue();
			if(fetchSize==null) fetchSize=100L;
			String topic = topicSelect.getValue().toString();
			String keywords = keyWordTxt.getValue().toString();
			CommonQuestionSearchCriteria searchCriteria = new CommonQuestionSearchCriteria();
			searchCriteria.setStartSearchDate(startDate);
			searchCriteria.setFetchSize(fetchSize);
			searchCriteria.setKeyWords(keywords);
			searchCriteria.setTypeName(topic);
			
			List<QnAPair> commonQnA = QnAServiceProvider.getCommonQnA(searchCriteria);
			beanItem.removeAllItems();
			beanItem.addAll(commonQnA);
		});

	}

	private void initMainScreen() {
		mainScreen.addStyleName(ValoTheme.LAYOUT_WELL);
		mainScreen.setMargin(new MarginInfo(false, true, false, true));

		Panel listQnAPanel = new Panel();
		listQnAPanel.addStyleName(ValoTheme.PANEL_WELL);
		listQnAPanel.addStyleName(NLPConstants.STYLE_QUESTION_PANEL_CUSTOM);
		listQnAPanel.setSizeFull();
		listQnAPanel.setCaption(MessageUtils.getString("QuestionScreen.tab.common-question.panel.list"));
		listQnAPanel.setHeight(LIST_QNA_PANEL_SIZE);

		beanItem = new BeanItemContainer<QnAPair>(QnAPair.class);
		ListQuestionTable commonQuestionTable = new ListQuestionTable(beanItem,false);
		listQnAPanel.setContent(commonQuestionTable);

		HorizontalLayout searchCriteriaBar = new HorizontalLayout();
		searchCriteriaBar.setSpacing(true);
		questionsStartDate = new PopupDateField(
				MessageUtils.getString("QuestionScreen.tab.common-question.start-date.caption"));

		topicSelect = new NativeSelect(
				MessageUtils.getString("QuestionScreen.tab.common-question.topic.caption"));
		topicSelect.setNullSelectionAllowed(false);
		topicSelect.addItems(Arrays.asList(NLPConstants.TOPICS));
		topicSelect.setNullSelectionAllowed(false);
		topicSelect.setValue(NLPConstants.TOPICS[0]);

		keyWordTxt = new TextField(
				MessageUtils.getString("QuestionScreen.tab.common-question.keyword.caption"));
		fetchSizeSelect = new NativeSelect(
				MessageUtils.getString("QuestionScreen.tab.common-question.search-size.caption"));
		fetchSizeSelect.setNullSelectionAllowed(false);
		fetchSizeSelect.setNullSelectionAllowed(false);
		fetchSizeSelect.addItems(Arrays.asList(NLPConstants.RESULT_SIZES));
		topicSelect.setValue(NLPConstants.RESULT_SIZES[0]);
		searchBtn = new Button(MessageUtils.getString("QuestionScreen.tab.common-question.btn.search"));
		searchBtn.setIcon(FontAwesome.SEARCH);
		searchBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		searchCriteriaBar.addComponent(questionsStartDate);
		searchCriteriaBar.addComponent(topicSelect);
		searchCriteriaBar.addComponent(keyWordTxt);
		searchCriteriaBar.addComponent(fetchSizeSelect);
		
		HorizontalLayout searchButtonBar = new HorizontalLayout();
		searchButtonBar.addComponent(searchBtn);
		searchButtonBar.setMargin(new MarginInfo(false, false, true, false));
		mainScreen.addComponent(searchCriteriaBar);
		mainScreen.addComponent(searchButtonBar);
		mainScreen.addComponent(listQnAPanel);

	}

}
