package vn.hcmuaf.nlp.ui.screen.question;

import org.vaadin.teemu.ratingstars.RatingStars;

import vn.hcmuaf.nlp.ui.model.QnAPair;
import vn.hcmuaf.nlp.util.MessageUtils;
import vn.hcmuaf.nlp.util.NLPConstants;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ListQuestionTable extends Table {
	private BeanItemContainer<QnAPair> beanItem;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_RELATE_QUESTION_SIZE = 5;
	
	private boolean showRating;

	public ListQuestionTable(BeanItemContainer<QnAPair> beanItem,boolean showRating) {
		this.showRating= showRating;
		this.beanItem = beanItem;
		setWidth(100, Unit.PERCENTAGE);
		setContainerDataSource(beanItem);
		setVisibleColumns(new Object[] { "question", "answer" });
		setColumnHeaders(new String[] { 
				MessageUtils.getString("QuestionScreen.qna.table.question"),
				MessageUtils.getString("QuestionScreen.qna.table.answer")
				});
		setColumnExpandRatio( "question", 0.5f);
		setColumnExpandRatio( "answer", 0.5f);
		setHeight(80,  Unit.PERCENTAGE);
		setColumnReorderingAllowed(false);
		addStyleName(ValoTheme.TABLE_COMPACT);
		setPageLength(DEFAULT_RELATE_QUESTION_SIZE);
		addRelateQuestionTableAction();
	}


	private void addRelateQuestionTableAction() {
		this.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				QnAPair pair = (QnAPair) event.getItemId();
				showAnswerPopupWindow(pair);
			}
		});

	}
	public void showAnswerPopupWindow(QnAPair qna){
		FormLayout QnAForm = new FormLayout();
		TextArea questionTxt = new TextArea(MessageUtils.getString("QuestionScreen.qna.table.question"));
		TextArea answerTxt = new TextArea(MessageUtils.getString("QuestionScreen.qna.table.answer"));
		RatingStars ratingStar = new RatingStars();
		ratingStar.setCaption(MessageUtils.getString("QuestionScreen.tab.new-question.label.feedbackScore"));
		ratingStar.setMaxValue(NLPConstants.RATING_STAR_MAX);
		ratingStar.setValue(NLPConstants.RATING_START_INIT);
		for(int i =0; i<NLPConstants.RATING_STAR_MAX;i++){
			ratingStar.setValueCaption(i+1, NLPConstants.STAR_CAPTIONS[i]);
		}
		Button okBtn = new Button("OK");
		ratingStar.setVisible(showRating);
		QnAForm.addComponent(questionTxt);
		QnAForm.addComponent(answerTxt);
		QnAForm.addComponent(ratingStar);
		QnAForm.addComponent(okBtn);
		QnAForm.setMargin(true);

		QnAForm.addStyleName(ValoTheme.PANEL_WELL);
		QnAForm.addStyleName(NLPConstants.STYLE_QUESTION_PANEL_CUSTOM);
		questionTxt.setValue(qna.getQuestion());
		String answerContent = qna.getAnswer();
		if(answerContent!=null &&(!answerContent.equals("null"))) {
			answerTxt.setValue(answerContent);
		}
		
		Window popupWindow = new Window();
		popupWindow.setContent(QnAForm);
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
		okBtn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				popupWindow.setVisible(false);
				
			}
		});
		
	}
	public void setBeanItem(BeanItemContainer<QnAPair> beanItem) {
		this.beanItem = beanItem;
	}

	public BeanItemContainer<QnAPair> getBeanItem() {
		return beanItem;
	}

}
