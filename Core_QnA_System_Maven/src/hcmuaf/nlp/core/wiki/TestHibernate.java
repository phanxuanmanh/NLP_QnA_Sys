package hcmuaf.nlp.core.wiki;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import hcmuaf.nlp.core.model.WikiPageContent;
import hcmuaf.nlp.core.util.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.BlobType;
import org.hibernate.type.IntegerType;

public class TestHibernate {
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		long start =System.currentTimeMillis();
		Query query = session
				.createSQLQuery("select old_id,old_text from Text where old_id = :old_id")
				.addScalar("old_id", new IntegerType())
				.addScalar("old_text", new BlobType());
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.setInteger("old_id", 22992321).list();
		for (Object[] row : rows) {
			WikiPageContent content = new WikiPageContent();
			content.setPageLatest(Integer.parseInt(row[0].toString()));
			Blob textBlob = (Blob) row[1];
			try {
				byte[] textData =textBlob.getBytes(1, (int) textBlob.length());
				content.setText(new String(textData));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(content.getText());
		}
		@SuppressWarnings("unchecked")
		List<Object[]> rows2 = query.setInteger("old_id", 22986510).list();
		for (Object[] row : rows2) {
			WikiPageContent content = new WikiPageContent();
			content.setPageLatest(Integer.parseInt(row[0].toString()));
			Blob textBlob = (Blob) row[1];
			try {
				byte[] textData =textBlob.getBytes(1, (int) textBlob.length());
				content.setText(new String(textData));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(content.getText());
		}
		long end =System.currentTimeMillis();
		System.out.println("total time spent: "+ (end-start));
		tx.commit();
		sessionFactory.close();
		
	}
}
