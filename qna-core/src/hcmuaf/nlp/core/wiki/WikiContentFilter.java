/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.dao.WikiConceptDao;
import hcmuaf.nlp.core.hibernateDao.impl.WikiConceptDaoImpl;
import hcmuaf.nlp.core.model.WikiPage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The Class WikiContentFilter.
 */
public class WikiContentFilter {

	/** The Constant LIST_CAT_URL. */
	private static final String LIST_CAT_URL = "https://vi.wikipedia.org/w/api.php?action=query&list=categorymembers&cmlimit=500&format=json&cmtype=subcat&cmtitle=Category:";
	
	/** The Constant LIST_PAGE_URL. */
	private static final String LIST_PAGE_URL = "https://vi.wikipedia.org/w/api.php?action=query&list=categorymembers&cmlimit=500&format=json&cmtype=page&cmtitle=Category:";
	
	/** The wiki concept dao. */
	private WikiConceptDao wikiConceptDao;

	/**
	 * Instantiates a new wiki content filter.
	 */
	public WikiContentFilter() {
		wikiConceptDao = new WikiConceptDaoImpl();
	}

	/**
	 * Gets the list sub category.
	 *
	 * @param parrentCateName the parrent cate name
	 * @return the list sub category
	 */
	public List<Integer> getListSubCategory(String parrentCateName) {
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

	/**
	 * Gets the list direct page.
	 *
	 * @param catName the cat name
	 * @return the list direct page
	 */
	public List<Integer> getListDirectPage(String catName) {
		List<Integer> listPageID = new ArrayList<Integer>();
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(LIST_PAGE_URL + catName);
		String response = target.request(MediaType.APPLICATION_JSON).get()
				.readEntity(String.class);
		if (response.indexOf("categorymembers") > 2) {
			String jsonData = response.substring(
					response.indexOf("categorymembers") - 2, response.length());
			JSONObject jsonObject = new JSONObject(jsonData);
			JSONArray listPage = jsonObject.getJSONArray("categorymembers");
			for (int i = 0; i < listPage.length(); i++) {
				JSONObject pageDetail = listPage.getJSONObject(i);
				int pageId = pageDetail.getInt("pageid");
				listPageID.add(new Integer(pageId));
			}
			System.out.println("category " + catName + " has number of Pages: "
					+ listPageID.size());
		}
		return listPageID;
	}

	/**
	 * Gets the list page.
	 *
	 * @param rootCat the root cat
	 * @param startLevel the start level
	 * @param depth the depth
	 * @return the list page
	 */
	public Set<Integer> getListPage(String rootCat, int startLevel, int depth) {
		Set<Integer> listPageID = new HashSet<Integer>();
		listPageID.addAll(getListDirectPage(rootCat));
		List<Integer> listSubCat = getListSubCategory(rootCat);
		for (int pageID : listSubCat) {
			String title = wikiConceptDao.getPageTitle(pageID);
			System.out.println("start on page : " + pageID + " title : "
					+ title);
			if (startLevel < depth) {
				if (title != null)
					listPageID
							.addAll(getListPage(title, startLevel + 1, depth));
			} else {
				return listPageID;
			}
		}
		return listPageID;
	}

	/**
	 * Update proccessing page status.
	 *
	 * @param parentPage the parent page
	 * @param dept the dept
	 */
	public void updateProccessingPageStatus(String parentPage, int dept) {
		Set<Integer> listPage = getListPage(parentPage, 1, dept);
		System.out.println("Search page done, start to update "
				+ listPage.size() + " pages");
		WikiConceptDao conceptDao = new WikiConceptDaoImpl();
		for (int pageID : listPage) {
			WikiPage page = conceptDao.getPage(pageID);
			if (page != null) {
				page.setPageInProcess(true);
				conceptDao.updatePage(page);
			}
		}
		System.out.println("Finish update " + listPage.size() + " pages");
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		WikiContentFilter filter = new WikiContentFilter();
		filter.updateProccessingPageStatus("Giáo_dục", 4);
		filter.updateProccessingPageStatus("Khoa_học_tự_nhiên", 4);
		filter.updateProccessingPageStatus("Khoa_học_xã_hội", 4);
		filter.updateProccessingPageStatus("Kỹ_thuật", 4);
		filter.updateProccessingPageStatus("Văn_hóa", 3);
	}
}
