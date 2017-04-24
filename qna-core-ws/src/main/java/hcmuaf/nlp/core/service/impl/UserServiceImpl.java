package hcmuaf.nlp.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmuaf.nlp.core.dao.RoleDao;
import hcmuaf.nlp.core.dao.UserDao;
import hcmuaf.nlp.core.hibernateDao.impl.RoleDaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.UserDaoImpl;
import hcmuaf.nlp.core.model.Role;
import hcmuaf.nlp.core.model.User;
import hcmuaf.nlp.core.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public User getUserById(Integer id) {
		User user = userDao.getUser(id);
		if (user != null) {
			user.setPassWord(null);
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = userDao.getUser(email);
		System.out.println(email);
		if (user != null) {
			user.setPassWord(null);
		}
		return user;
	}

	@Override
	public User addUser(User user) {
		userDao = new UserDaoImpl();
		roleDao = new RoleDaoImpl();
		Role userRole = roleDao.getRoleByName("user");
		user.setRole(userRole);
		userDao.addUser(user);
		return user;
	}

	@Override
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}

}
