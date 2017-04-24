package vn.hcmuaf.nlp.ui.service.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import vn.hcmuaf.nlp.ui.model.User;
import vn.hcmuaf.nlp.ui.service.LoginService;
import vn.hcmuaf.nlp.util.NLPConstants;
import vn.hcmuaf.nlp.util.RestURIProvider;

public class LoginServiceImpl implements LoginService {
	private boolean isLoggedIn = false;
	private User currentUser;

	@Override
	public boolean getLoginStatus() {
		return isLoggedIn;
	}

	@Override
	public void setLoginStatus(boolean status) {
		this.isLoggedIn = status;
	}

	@Override
	public boolean login(String email, String passWord) throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.LOGIN_SERVICE_PREFIX);
		User user = new User();
		user.setEmail(email);
		user.setPassWord(passWord);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> response = restTemplate.postForEntity(uri, user, Boolean.class);
		boolean status = response.getBody();
		setLoginStatus(status);
		if(status){
			currentUser = getUserByEmail(email);
		}
		return status;
	}

	@Override
	public User getUserByEmail(String email) throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.USER_SERVICE_PREFIX, NLPConstants.USER_SERVICE_GET_BY_EMAIL,
				email, "");
		System.out.println(uri.toString());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<User> response = restTemplate.exchange(uri, HttpMethod.POST, null, User.class);
		return response.getBody();

	}

	@Override
	public User getUserById(int id) throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
				NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
		URI uri = restURIProvider.getURI(NLPConstants.USER_SERVICE_PREFIX, NLPConstants.USER_SERVICE_GET_BY_ID,
				String.valueOf(id));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<User> response = restTemplate.getForEntity(uri, User.class);
		return response.getBody();
	}

	@Override
	public void addUser(User user) {
		RestURIProvider restURIProvider;
		try {
			restURIProvider = new RestURIProvider(NLPConstants.HTTP, NLPConstants.SERVICE_HOST,
					NLPConstants.SERVICE_PORT, NLPConstants.QNA_CORE_WS);
			URI uri = restURIProvider.getURI(NLPConstants.USER_ADD_SERVICE_POSTFIX);
			System.out.println(uri.toString());
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForEntity(uri, user, String.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
