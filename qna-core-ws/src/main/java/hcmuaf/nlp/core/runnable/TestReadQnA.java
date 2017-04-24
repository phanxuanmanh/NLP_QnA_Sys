package hcmuaf.nlp.core.runnable;

import static java.util.Comparator.comparing;
import hcmuaf.nlp.core.model.QnAPairEntity;

import java.util.ArrayList;
import java.util.List;

public class TestReadQnA {
	public static void main(String[] args) {
		List<QnAPairEntity> listQna = new ArrayList<QnAPairEntity>();
		listQna.stream().sorted(comparing(QnAPairEntity::getAnswer));
	}

}
