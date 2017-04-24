package hcmuaf.nlp.core.runnable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestHibernate {
	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("'%m/%d/%Y");
		Date from = new Date();
		String dateStr = dateFormat.format(from );
		String query = "SELECT ref_q_id, COUNT(q_id)"
				+ " FROM question_history"
				+ " where ref_q_id is not null "
				+ "and created_date > STR_TO_DATE('" +dateStr+"'"+",%m/%d/%Y') "
				+ "GROUP BY ref_q_id order by COUNT(q_id) desc";
		System.out.println(query);
	}
}
