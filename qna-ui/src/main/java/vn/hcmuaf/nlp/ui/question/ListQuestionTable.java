package vn.hcmuaf.nlp.ui.question;

import org.vaadin.teemu.ratingstars.RatingStars;

import vn.hcmuaf.nlp.ui.model.QnAPairModel;
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
	private BeanItemContainer<QnAPairModel> beanItem;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_RELATE_QUESTION_SIZE = 5;

	public ListQuestionTable(BeanItemContainer<QnAPairModel> beanItem) {
		this.beanItem = beanItem;
		setSizeFull();
		setContainerDataSource(beanItem);
		setVisibleColumns(new Object[] { "questionId", "questionContent" });
		setColumnHeaders(new String[] { "Mã câu hỏi", "Nội dung" });
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
				QnAPairModel pair = (QnAPairModel) event.getItemId();
				showAnswerPopupWindow(pair);
			}
		});

	}
	public void showAnswerPopupWindow(QnAPairModel qna){
		FormLayout QnAForm = new FormLayout();
		TextArea questionTxt = new TextArea("Question");
		TextArea answerTxt = new TextArea("Answer");
		RatingStars ratingStar = new RatingStars();
		ratingStar.setCaption(MessageUtils.getString("QuestionScreen.tab.new-question.label.feedbackScore"));
		ratingStar.setMaxValue(NLPConstants.RATING_STAR_MAX);
		ratingStar.setValue(NLPConstants.RATING_START_INIT);
		for(int i =0; i<NLPConstants.RATING_STAR_MAX;i++){
			ratingStar.setValueCaption(i+1, NLPConstants.STAR_CAPTION[i]);
		}
		Button okBtn = new Button("Ok");
		
		QnAForm.addComponent(questionTxt);
		QnAForm.addComponent(answerTxt);
		QnAForm.addComponent(ratingStar);
		QnAForm.addComponent(okBtn);

		QnAForm.addStyleName(ValoTheme.PANEL_WELL);
		QnAForm.addStyleName(NLPConstants.STYLE_QUESTION_PANEL_CUSTOM);
		questionTxt.setValue(qna.getQuestionContent());
		answerTxt.setValue(qna.getAnswerContent());
		
		Window popupWindow = new Window();
		popupWindow.setContent(QnAForm);
		popupWindow.setWidth("400px");
		popupWindow.center();
		popupWindow.setClosable(false);
		popupWindow.setResizable(false);
		questionTxt.setSizeFull();
		answerTxt.setSizeFull();
		UI.getCurrent().addWindow(popupWindow);
		popupWindow.setModal(true);
		okBtn.addClickListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				popupWindow.setVisible(false);
				
			}
		});
		
	}
	public void setBeanItem(BeanItemContainer<QnAPairModel> beanItem) {
		this.beanItem = beanItem;
	}

	public BeanItemContainer<QnAPairModel> getBeanItem() {
		return beanItem;
	}

}
