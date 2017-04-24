package vn.hcmuaf.nlp.manage.screen.question;

import java.util.Locale;

import vn.hcmuaf.nlp.manage.service.QuestionHistoryServiceProvider;

import com.vaadin.data.util.converter.Converter;

public class QuestionIdToAnswerConverter implements Converter<String, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer convertToModel(String value, Class<? extends Integer> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return null;
	}

	@Override
	public String convertToPresentation(Integer value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if(value==null){
			return null;
		}else{
			String answer = QuestionHistoryServiceProvider.getAnswerByQuestionId(value);
			return answer;
		}
	}

	@Override
	public Class<Integer> getModelType() {
		return Integer.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
