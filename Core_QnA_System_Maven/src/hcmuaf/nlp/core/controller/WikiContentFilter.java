package hcmuaf.nlp.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONObject;

public class WikiContentFilter {
	private static final String LIST_CAT_URL = "https://vi.wikipedia.org/w/api.php?action=query&list=categorymembers&cmlimit=500&format=json&cmtype=subcat&cmtitle=Category:";
	private static final String LIST_PAGE_URL = "https://vi.wikipedia.org/w/api.php?action=query&list=categorymembers&cmlimit=500&format=json&cmtitle=Category:";

	public List<Integer> getListSubCategoryByCategory(String parrentCateName) {
		List<Integer> listPageID = new ArrayList<Integer>();
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client
				.target(LIST_CAT_URL + parrentCateName);
		String response = target.request(MediaType.APPLICATION_JSON).get()
				.readEntity(String.class);
		String jsonData = response.substring(
				response.indexOf("categorymembers") - 2, response.length());
		JSONObject jsonObject = new JSONObject(jsonData);
		JSONArray listPage = jsonObject.getJSONArray("categorymembers");
		for (int i = 0; i < listPage.length(); i++) {
			JSONObject pageDetail = listPage.getJSONObject(i);
			int pageId = pageDetail.getInt("pageid");
			listPageID.add(new Integer(pageId));
		}
		return listPageID;
	}

	public List<Integer> getListPageByCategory(String catName) {
		List<Integer> listPageID = new ArrayList<Integer>();
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(LIST_PAGE_URL + catName);
		String response = target.request(MediaType.APPLICATION_JSON).get()
				.readEntity(String.class);
		String jsonData = response.substring(
				response.indexOf("categorymembers") - 2, response.length());
		JSONObject jsonObject = new JSONObject(jsonData);
		JSONArray listPage = jsonObject.getJSONArray("categorymembers");
		for (int i = 0; i < listPage.length(); i++) {
			JSONObject pageDetail = listPage.getJSONObject(i);
			int pageId = pageDetail.getInt("pageid");
			listPageID.add(new Integer(pageId));
		}
		return listPageID;
	}

	
}
