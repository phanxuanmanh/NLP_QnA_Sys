package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.UserDao;
import hcmuaf.nlp.core.model.User;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public User getUser(int userId) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, userId);
		return user;
	}
	
	@Override
	public void addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}
	
	@Override
	public boolean isValid(String email, String password) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> users = session.createCriteria(User.class).add(Restrictions.eq("email", email))
				.add(Restrictions.eq("passWord", password)).list();
		if (users != null && users.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public User getUser(String email) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult();
		return user;
	}
	
	@Override
	public void deleteUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
	}
}
