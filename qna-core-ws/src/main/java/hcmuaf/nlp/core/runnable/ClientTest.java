package hcmuaf.nlp.core.runnable;

import hcmuaf.nlp.core.model.User;
import hcmuaf.nlp.service.util.RestURIProvider;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

public class ClientTest {
	public static List<User> getListUser() throws URISyntaxException {
		RestURIProvider restURIProvider = new RestURIProvider("http",
				"localhost", 8080, "/qna-core-ws");
		URI uri = restURIProvider.getURI("user/");
		System.out.println(uri.toString());
		RestTemplate restTemplat = new RestTemplate();
		User[] list= restTemplat.getForObject(uri, User[].class);
		return Arrays.asList(list);
	}
}
