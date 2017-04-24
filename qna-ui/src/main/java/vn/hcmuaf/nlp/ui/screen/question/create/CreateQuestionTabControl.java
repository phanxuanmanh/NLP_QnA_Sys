package vn.hcmuaf.nlp.ui.screen.question.create;

import java.util.Date;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.ConfirmDialog.Listener;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import vn.hcmuaf.nlp.ui.model.QnAPair;
import vn.hcmuaf.nlp.ui.model.QuestionHistory;
import vn.hcmuaf.nlp.ui.service.QnAServiceProvider;
import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

public class CreateQuestionTabControl {
	private CreateQuestionTab createQuestionTab;
	private Integer currentQuestionId;
	
	public CreateQuestionTabControl(CreateQuestionTab createQuestionTab) {
		this.createQuestionTab = createQuestionTab;
	}
	
	public void initActionHandle() {
		handleSubmitQuestion();
		handleSendFeedBack();
		handleNewQuestionBtn();
		
	}
	
	private void handleNewQuestionBtn() {
		createQuestionTab.getNewQuestionBtn().addClickListener(event->
		{
			clearResultAndNew();
		});
		
	}

	private void clearResultAndNew() {
		createQuestionTab.hideQnAResultComponent();
		createQuestionTab.getSendQuestionBtn().setVisible(true);
		createQuestionTab.getNewQuestionBtn().setVisible(false);
	}

	private void handleSendFeedBack() {
		createQuestionTab.getSendFeedBackBtn().addClickListener(
				event -> {
					if (currentQuestionId != null) {
						QuestionHistory history = new QuestionHistory();
						history.setQuestionId(currentQuestionId);
						history.setRating(createQuestionTab.getQuestionRating());
						history.setCreatedDate(new Date());
						history.setCreateUserId(createQuestionTab.getQuestionScreenControl().getCurrentUser().getId());
						String selectedType = createQuestionTab.getFeebBackType().getValue().toString();
						if (!NLPConstants.FEEDBACK_TYPES[0].equals(selectedType)) {
							createQuestionTab.getRatingStar().setVisible(false);
							ConfirmDialog.show(UI.getCurrent(),
									MessageUtils.getString("QuestionScreen.tab.new-question.label.feedbackCaption"),
									MessageUtils.getString("QuestionScreen.tab.new-question.label.feedbackAction"),
									MessageUtils.getString("QuestionScreen.tab.new-question.label.feedbackOk"),
									MessageUtils.getString("QuestionScreen.tab.new-question.label.feedbackCancel"),
									dialog -> {
										if (dialog.isConfirmed()) {
											QnAServiceProvider.addQuestionHistory(history);
										}
									});
						} else {
							QnAPair referQnA = createQuestionTab.getRelateQnABeanItems().getIdByIndex(0);
							if (referQnA != null) {
								history.setReferenceQuestionId(referQnA.getQuestionId());
								QnAServiceProvider.addQuestionHistory(history);
							}
						}
						Notification.show("feedback sent");
					}
					createQuestionTab.getQuestionTxt().setReadOnly(false);
					clearResultAndNew();
				});
	}
	
	private void handleSubmitQuestion() {
		createQuestionTab.getSendQuestionBtn().addClickListener(event -> {
			createQuestionTab.getSendQuestionBtn().setVisible(false);
			createQuestionTab.getNewQuestionBtn().setVisible(true);
			List<QnAPair> qnaPairs = null;
			try {
				qnaPairs = QnAServiceProvider.getListRelateQuestion(createQuestionTab.getQuestionTxt().getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (qnaPairs != null) {
				currentQuestionId = qnaPairs.get(0).getQuestionId();
				if (qnaPairs.size() > 1) {
					createQuestionTab.showQnAResultComponent();
					QnAPair similarQuestionId = qnaPairs.get(1);
					createQuestionTab.getAnswerTxt().setReadOnly(false);
					createQuestionTab.getAnswerTxt().setValue(similarQuestionId.getAnswer());
					createQuestionTab.getAnswerTxt().setReadOnly(true);
					createQuestionTab.getQuestionTxt().setReadOnly(true);
					qnaPairs.remove(0);
					setListQuestionBeanItem(qnaPairs);
				} else {
					ConfirmDialog.Listener confirmFeddbackListener = createContentNotFoundDialog();
					ConfirmDialog.show(UI.getCurrent(),
							MessageUtils.getString("QuestionScreen.tab.new-question.notfound.caption"),
							MessageUtils.getString("QuestionScreen.tab.new-question.notfound.message"),
							MessageUtils.getString("QuestionScreen.tab.new-question.notfound.ok"),
							MessageUtils.getString("QuestionScreen.tab.new-question.notfound.cancel"),
							confirmFeddbackListener);
				}
			}
		});
	}

	private Listener createContentNotFoundDialog() {
		return new ConfirmDialog.Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClose(ConfirmDialog confirmLogin) {
				if (confirmLogin.isConfirmed()) {
					QuestionHistory history = new QuestionHistory();
					history.setQuestionId(currentQuestionId);
					history.setCreatedDate(new Date());
					history.setCreateUserId(createQuestionTab.getQuestionScreenControl().getCurrentUser()
							.getId());
					QnAServiceProvider.addQuestionHistory(history);
					clearResultAndNew();
					Notification.show("Sent");
				}
			}
		};
	}
	
	private void setListQuestionBeanItem(List<QnAPair> listQnA) {
		createQuestionTab.getRelateQnABeanItems().removeAllItems();
		createQuestionTab.getRelateQnABeanItems().addAll(listQnA);
	}
}
