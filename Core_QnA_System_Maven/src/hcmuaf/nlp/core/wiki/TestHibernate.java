package hcmuaf.nlp.core.wiki;

import java.util.List;

import hcmuaf.nlp.core.model.Question;
import hcmuaf.nlp.core.util.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TestHibernate {
public static void main(String[] args) {
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session = sessionFactory.getCurrentSession();
	Transaction tx =session.beginTransaction();
	Query query = session.createQuery("from Question");
	@SuppressWarnings("unchecked")
	List<Question> listQuestion = query.list();
	System.out.println(listQuestion.size());
	tx.commit();
	sessionFactory.close();
}
}
