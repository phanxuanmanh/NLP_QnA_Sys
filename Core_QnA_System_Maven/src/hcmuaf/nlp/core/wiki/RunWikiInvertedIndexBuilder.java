package hcmuaf.nlp.core.wiki;

import java.util.List;

public class RunWikiInvertedIndexBuilder {

	public static void main(String[] args) {
		 WikiInvertedIndexBuilder buider = new WikiInvertedIndexBuilder();
		 List<Integer> listPage = buider.getListPageId();
		 for(Integer pageId : listPage){
			 if(pageId>21253162)
			 buider.conceptProcess(pageId.intValue());
		 }
	}

}
