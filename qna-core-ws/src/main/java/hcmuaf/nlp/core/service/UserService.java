/**
 * @author Manh Phan
 *
 * Edited date Aug 17, 2016
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.model.User;

/**
 * The Interface UserService.
 */
public interface UserService {
	
	/**
	 * Gets the user by email.
	 *
	 * @param email the email
	 * @return the user by email
	 */
	public User getUserByEmail(String email);

	/**
	 * Gets the user by id.
	 *
	 * @param id the id
	 * @return the user by id
	 */
	public User getUserById(Integer id);

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User addUser(User user);

	/**
	 * Delete user.
	 *
	 * @param user the user
	 */
	public void deleteUser(User user);
}
