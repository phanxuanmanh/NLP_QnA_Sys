package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.RoleDao;
import hcmuaf.nlp.core.model.Role;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<Role> getRoles() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Role> rolse = session.createCriteria(Role.class).list();
		return rolse;
	}
	
	@Override
	public Role getRoleByName(String roleName) {
		Session session = sessionFactory.getCurrentSession();
		Role role = (Role) session.createCriteria(Role.class).add(Restrictions.eq("roleName", roleName)).uniqueResult();
		return role;
	}
}
