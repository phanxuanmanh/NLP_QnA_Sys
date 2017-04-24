package vn.hcmuaf.nlp.manage.screen.answer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.hcmuaf.nlp.manage.service.QuestionHistoryServiceProvider;
import vn.hcmuaf.nlp.ui.model.UnAnsweredQnA;
import vn.hcmuaf.nlp.ui.model.User;
import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class AnswerQuestionScreenControl {
	private AnswerQuestionScreen mainScreen;
	private BeanItemContainer<UnAnsweredQnA> unAnsweredQuestionBean;
	private Grid qnaGrid;
	private static final String FORMAT = "%1$td/%1$tm/%1$tY";
	
	public AnswerQuestionScreenControl(AnswerQuestionScreen answerQuestionScreen) {
		this.mainScreen = answerQuestionScreen;
	}
	
	public void initScreen() {
		mainScreen.addStyleName(ValoTheme.LAYOUT_WELL);
		mainScreen.setMargin(new MarginInfo(false, false, false, false));
		Panel listQuestionPanel = new Panel();
		listQuestionPanel.addStyleName(ValoTheme.PANEL_WELL);
		listQuestionPanel.addStyleName(NLPConstants.STYLE_QUESTION_PANEL_CUSTOM);
		listQuestionPanel.setSizeFull();
		listQuestionPanel.setCaption(MessageUtils.getString("QuestionScreen.tab.common-question.panel.list"));
		listQuestionPanel.setHeight(NLPConstants.LIST_QNA_PANEL_SIZE);
		initTable();
		listQuestionPanel.setContent(qnaGrid);
		mainScreen.addComponent(listQuestionPanel);
	}
	
	private void initTable() {
		List<UnAnsweredQnA> data = initData();
		unAnsweredQuestionBean = new BeanItemContainer<UnAnsweredQnA>(UnAnsweredQnA.class);
		unAnsweredQuestionBean.addAll(data);
		GeneratedPropertyContainer generatedBean = new GeneratedPropertyContainer(unAnsweredQuestionBean);
		generatedBean.addGeneratedProperty("reply", new PropertyValueGenerator<String>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getValue(Item item, Object itemId, Object propertyId) {
				return MessageUtils.getString("ManagerScreen.tab.answer.table.answer");
			}
			
			@Override
			public Class<String> getType() {
				return String.class;
			}
		});
		qnaGrid = new Grid();
		qnaGrid.setContainerDataSource(generatedBean);
		qnaGrid.setWidth(100f, Unit.PERCENTAGE);
		qnaGrid.getColumn("reply").setRenderer(new ButtonRenderer(event -> {
			Object itemId = event.getItemId();
			showAnswerDialog(itemId);
		}));
		qnaGrid.getColumn("createdDate").setRenderer(new DateRenderer(FORMAT));
		qnaGrid.setColumns("question", "createUserName", "createdDate", "reply");
		qnaGrid.setColumnOrder("question", "createUserName", "createdDate", "reply");
		qnaGrid.getColumn("question").setMaximumWidth(500);
		qnaGrid.getColumn("question").setExpandRatio(5)
				.setHeaderCaption(MessageUtils.getString("ManagerScreen.tab.answer.table.question.content"));
		qnaGrid.getColumn("createUserName").setExpandRatio(2).setSortable(true)
				.setHeaderCaption(MessageUtils.getString("ManagerScreen.tab.answer.table.created.person"));
		qnaGrid.getColumn("createdDate").setExpandRatio(2).setSortable(true)
				.setHeaderCaption(MessageUtils.getString("ManagerScreen.tab.answer.table.created.date"));
		qnaGrid.getColumn("reply").setExpandRatio(1);
	}
	
	private void showAnswerDialog(Object itemId) {
		UnAnsweredQnA qna = unAnsweredQuestionBean.getItem(itemId).getBean();
		FormLayout questionHistoryForm = new FormLayout();	
	    PopupDateField 	questionsExpiredDate = new PopupDateField(
				MessageUtils.getString("ManagerScreen.tab.manage.table.expired"));
	    Calendar currenCalendar= Calendar.getInstance();
	    currenCalendar.add(Calendar.DATE, 1);
	    questionsExpiredDate.setRangeStart(currenCalendar.getTime());
	    TextArea questionTxt = new TextArea(MessageUtils.getString("ManagerScreen.tab.manage.table.question.content"));
		TextArea answerTxt = new TextArea(MessageUtils.getString("ManagerScreen.tab.manage.table.answer"));
		questionTxt.setValue(qna.getQuestion().getContent());
		questionTxt.setSizeFull();
		answerTxt.setSizeFull();
		questionTxt.setReadOnly(true);
		HorizontalLayout buttonBar = new HorizontalLayout();
		Button okBtn = new Button(MessageUtils.getString("ManagerScreen.tab.answer.popup.answer"));
		Button cancel = new Button(MessageUtils.getString("ManagerScreen.tab.answer.popup.close"));
		okBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		cancel.setStyleName(ValoTheme.BUTTON_DANGER);
		buttonBar.setSpacing(true);
		buttonBar.addComponent(okBtn);
		buttonBar.addComponent(cancel);
		questionHistoryForm.addComponent(questionTxt);
		questionHistoryForm.addComponent(answerTxt);
		questionHistoryForm.addComponent(questionsExpiredDate);
		questionHistoryForm.addComponent(buttonBar);
		questionHistoryForm.setMargin(true);
		questionHistoryForm.addStyleName(ValoTheme.PANEL_WELL);
		questionHistoryForm.addStyleName(NLPConstants.STYLE_QUESTION_PANEL_CUSTOM);
		Window popupWindow = new Window();
		popupWindow.setContent(questionHistoryForm);
		popupWindow.setWidth("700px");
		popupWindow.center();
		popupWindow.setClosable(false);
		popupWindow.setResizable(false);
		UI.getCurrent().addWindow(popupWindow);
		popupWindow.setModal(true);
		cancel.addClickListener(event -> popupWindow.setVisible(false));
		okBtn.addClickListener(event ->{ 
			popupWindow.setVisible(false);
			User answerUser = mainScreen.getLoginService().getCurrentUser();
			Date expiredDate = questionsExpiredDate.getValue();
			qna.setAnswer(answerTxt.getValue());
			qna.setExpiredDate(expiredDate);
			qna.setAnswerUserName(answerUser.getEmail());
			qna.setAnswerUserId(answerUser.getId());
			QuestionHistoryServiceProvider.addAnswer(qna);
			unAnsweredQuestionBean.removeItem(itemId);});
		
	}
	
	private List<UnAnsweredQnA> initData() {
		Calendar lastMoth = Calendar.getInstance();
		lastMoth.add(Calendar.YEAR, -1);
		return QuestionHistoryServiceProvider.getListUnAnswerQuestion(lastMoth.getTime());
	}
}
