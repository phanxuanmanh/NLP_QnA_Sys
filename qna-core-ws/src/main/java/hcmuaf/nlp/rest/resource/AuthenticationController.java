/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.rest.resource;

import hcmuaf.nlp.core.dao.UserDao;
import hcmuaf.nlp.core.model.User;
import hcmuaf.nlp.core.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class AuthenticationController.
 */
@RestController
public class AuthenticationController {
	
	/** The user service. */
	@Autowired
	private UserService userService;
	
	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/**
	 * Check valid.
	 *
	 * @param user the user
	 * @return the response entity
	 */
	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	public ResponseEntity<Boolean> checkValid(@RequestBody User user) {
		boolean isValid = userDao.isValid(user.getEmail(), user.getPassWord());
		System.out.println("get login result");
		return new ResponseEntity<Boolean>(isValid, HttpStatus.OK);
	}

	/**
	 * Gets the user.
	 *
	 * @param email the email
	 * @return the user
	 */
	@RequestMapping(value = "/user/getByEmail/{email}/", method = RequestMethod.POST)
	public ResponseEntity<User> getUser(@PathVariable("email") String email) {
		User user = userService.getUserByEmail(email);
		System.out.println("response User");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Gets the user.
	 *
	 * @param id the id
	 * @return the user
	 */
	@RequestMapping(value = "/user/getById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		User user = userService.getUserById(id);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Add the user.
	 *
	 * @param user the user
	 * @return the response entity
	 */
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody User user) {
		userService.addUser(user);
		System.out.println("response User");
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
}
