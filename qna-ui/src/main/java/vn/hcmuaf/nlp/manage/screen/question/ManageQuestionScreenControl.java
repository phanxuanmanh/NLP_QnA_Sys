package vn.hcmuaf.nlp.manage.screen.question;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.ConfirmDialog.Listener;

import vn.hcmuaf.nlp.manage.service.QuestionHistoryServiceProvider;
import vn.hcmuaf.nlp.ui.model.QuestionHistory;
import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ManageQuestionScreenControl implements Serializable{
	private static final long serialVersionUID = 1L;
	private ManageQuestionScreen mainScreen;
	private BeanItemContainer<QuestionHistory> historyBeans;
	private Table listQuestionTable;
	

	public ManageQuestionScreenControl(ManageQuestionScreen mainScreen) {
		this.mainScreen = mainScreen;
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
		listQuestionPanel.setContent(listQuestionTable);
		mainScreen.addComponent(listQuestionPanel);

	}

	private void initTable() {
		listQuestionTable = new Table();
		listQuestionTable.setSortEnabled(true);
		List<QuestionHistory> listHistory = initData();
		historyBeans = new BeanItemContainer<QuestionHistory>(QuestionHistory.class);
		historyBeans.addAll(listHistory);
		GeneratedPropertyContainer generatedBean = new GeneratedPropertyContainer(historyBeans);
		
		listQuestionTable.addGeneratedColumn("delete", new ColumnGenerator() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Button delete = new Button(FontAwesome.TRASH);
				delete.setStyleName(ValoTheme.BUTTON_DANGER);
				delete.setDescription(MessageUtils.getString("ManagerScreen.tab.manage.table.delete"));;
				delete.addClickListener(e -> {
					ConfirmDialog.Listener confirmFeddbackListener = createDeleteQuestionConfirmDialog(itemId);
					ConfirmDialog.show(UI.getCurrent(),
							MessageUtils.getString("ManagerScreen.tab.manage.table.delelet.confirm.title"),
							MessageUtils.getString("ManagerScreen.tab.manage.table.delelet.confirm.content"),
							MessageUtils.getString("QuestionScreen.tab.new-question.notfound.ok"),
							MessageUtils.getString("QuestionScreen.tab.new-question.notfound.cancel"),
							confirmFeddbackListener);
					
				});
				return delete;
			}
		});
		listQuestionTable.setContainerDataSource(generatedBean);
		listQuestionTable.setWidth(100f,Unit.PERCENTAGE);
		listQuestionTable.setVisibleColumns(new Object[] {"questionId", "referenceQuestionId", "createdDate",
				"createUserId", "rating", "delete" });
		listQuestionTable.setColumnExpandRatio("questionId", 0.3f);
		listQuestionTable.setColumnExpandRatio("referenceQuestionId", 0.3f);
		listQuestionTable.setColumnExpandRatio("createdDate", 0.1f);
		listQuestionTable.setColumnExpandRatio("createUserId", 0.1f);
		listQuestionTable.setColumnExpandRatio("rating", 0.1f);
		listQuestionTable.setColumnExpandRatio("delete", 0.1f);
		listQuestionTable.setColumnHeaders(
				MessageUtils.getString("ManagerScreen.tab.manage.table.question.content"), 
				MessageUtils.getString("ManagerScreen.tab.manage.table.answer"),
				MessageUtils.getString("ManagerScreen.tab.manage.table.created.date"),
				MessageUtils.getString("ManagerScreen.tab.manage.table.created.person"),
				MessageUtils.getString("ManagerScreen.tab.manage.table.vote"), 
				MessageUtils.getString("ManagerScreen.tab.manage.table.action"));
		listQuestionTable.setConverter("questionId", new QuestionIdToContentConverter());
		listQuestionTable.setConverter("referenceQuestionId", new QuestionIdToAnswerConverter());
		listQuestionTable.setConverter("createUserId", new UserIdToEmailConverter());
		listQuestionTable.setConverter("createdDate", new StringToDateConverter(){
			private static final long serialVersionUID = 1L;

			@Override
			protected DateFormat getFormat(Locale locale) {
				return  new SimpleDateFormat("dd/MM/yyyy");
			}
		});
		listQuestionTable.addItemClickListener(event -> {
			//Show confirm dialog 
			
			Item selected = event.getItem();
			Integer questionId =(Integer) selected.getItemProperty("questionId").getValue();
			QuestionHistory history = new QuestionHistory();
			history.setQuestionId(questionId);
			showHistoryPopUp(history);
		});
		
		listQuestionTable.setSortContainerPropertyId("createdDate");
	}
	private static void showHistoryPopUp(QuestionHistory history){
		FormLayout questionHistoryForm = new FormLayout();
		TextArea questionTxt = new TextArea(MessageUtils.getString("ManagerScreen.tab.manage.table.question.content"));
		TextArea answerTxt = new TextArea(MessageUtils.getString("ManagerScreen.tab.manage.table.answer"));
		Button okBtn = new Button(MessageUtils.getString("ManagerScreen.tab.manage.popup.close"));

		questionHistoryForm.addComponent(questionTxt);
		questionHistoryForm.addComponent(answerTxt);
		questionHistoryForm.addComponent(okBtn);
		questionHistoryForm.setMargin(true);
		questionHistoryForm.addStyleName(ValoTheme.PANEL_WELL);
		questionHistoryForm.addStyleName(NLPConstants.STYLE_QUESTION_PANEL_CUSTOM);
		questionTxt.setValue(QuestionHistoryServiceProvider.getQuestionByQuestionId(history.getQuestionId()));
		answerTxt.setValue( QuestionHistoryServiceProvider.getAnswerByQuestionId(history.getQuestionId()));
	
		Window popupWindow = new Window();
		popupWindow.setContent(questionHistoryForm);
		popupWindow.setWidth("700px");
		popupWindow.center();
		popupWindow.setClosable(false);
		popupWindow.setResizable(false);
		questionTxt.setSizeFull();
		answerTxt.setSizeFull();
		questionTxt.setReadOnly(true);
		answerTxt.setReadOnly(true);
		UI.getCurrent().addWindow(popupWindow);
		popupWindow.setModal(true);
		okBtn.addClickListener(event->	popupWindow.setVisible(false));
		okBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
	}
	private Listener createDeleteQuestionConfirmDialog(Object itemId) {
		QuestionHistory history = (QuestionHistory) itemId;
		return new ConfirmDialog.Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClose(ConfirmDialog confirmLogin) {
				if (confirmLogin.isConfirmed()) {
					QuestionHistoryServiceProvider.deleteHistory(history.getId());
					historyBeans.removeItem(itemId);
					Notification.show("Sent");
				}
			}
		};
	}
	private List<QuestionHistory> initData() {
		Calendar lastMoth = Calendar.getInstance();
		lastMoth.add(Calendar.MONTH, -1);
		return QuestionHistoryServiceProvider.getListRecentQuestionHistory(lastMoth.getTime(), 100, 1);
	}

}
