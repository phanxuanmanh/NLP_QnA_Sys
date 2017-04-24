/**
 * @author Manh Phan
 *
 * Edited date Jul 30, 2016
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.User;

/**
 * The Interface UserDao.
 */
public interface UserDao {

	/**
	 * Gets the user.
	 *
	 * @param userId
	 *            the user id
	 * @return the user
	 */
	public User getUser(int userId);
	
	/**
	 * Gets the user.
	 *
	 * @param email the email
	 * @return the user
	 */
	public User getUser(String email);

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	public void addUser(User user);
	
	/**
	 * Checks if is valid.
	 *
	 * @param email the email
	 * @param password the password
	 * @return true, if is valid
	 */
	public boolean isValid(String email, String password);
	
	/**
	 * Delete user.
	 *
	 * @param user the user
	 */
	public void deleteUser(User user);
}
