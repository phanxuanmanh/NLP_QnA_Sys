/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.runnable;

import hcmuaf.nlp.core.wiki.WikiInvertedIndexBuilder;

import java.util.List;

/**
 * The Class RunWikiInvertedIndexBuilder.
 */
public class RunWikiInvertedIndexBuilder {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		 WikiInvertedIndexBuilder buider = new WikiInvertedIndexBuilder();
		 List<Integer> listPage = buider.getListPageId();
		 for(Integer pageId : listPage){
			 if(pageId>21253162)
			 buider.conceptProcess(pageId.intValue());
		 }
	}

}
