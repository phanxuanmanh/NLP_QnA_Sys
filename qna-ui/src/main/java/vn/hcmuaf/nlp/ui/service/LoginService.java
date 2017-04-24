/**
 * @author Manh Phan
 *
 * Edited date Aug 2, 2016
 */
package vn.hcmuaf.nlp.ui.service;

import java.net.URISyntaxException;

import vn.hcmuaf.nlp.ui.model.User;

/**
 * The Interface LoginService.
 */
public interface LoginService {
	/**
	 * Gets the login status.
	 *
	 * @return the login status
	 */
	public boolean getLoginStatus();
	
	/**
	 * Sets the login status.
	 *
	 * @param status
	 *            the new login status
	 */
	public void setLoginStatus(boolean status);
	
	/**
	 * Login.
	 *
	 * @param email
	 *            the email
	 * @param passWord
	 *            the pass word
	 * @return true, if successful
	 * @throws URISyntaxException
	 */
	public boolean login(String email, String passWord) throws URISyntaxException;
	
	/**
	 * Gets the user.
	 *
	 * @param email
	 *            the email
	 * @return the user
	 * @throws URISyntaxException
	 */
	public User getUserByEmail(String email) throws URISyntaxException;
	
	public User getUserById(int id) throws URISyntaxException;
	
	/**
	 * Adds the user.
	 *
	 * @param user
	 *            the user
	 */
	public void addUser(User user);
	
	public User getCurrentUser();
	
	public void setCurrentUser(User currentUser);
}
