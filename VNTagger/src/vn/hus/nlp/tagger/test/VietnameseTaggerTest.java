/**
 * 
 */
package vn.hus.nlp.tagger.test;

import java.util.List;

import edu.stanford.nlp.ling.WordTag;
import vn.hus.nlp.tagger.IConstants;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 * <br>
 * Apr 9, 2009, 7:34:28 PM
 * <br>
 * Test class for the Vietnamese tagger.
 */
public class VietnameseTaggerTest {
	
	public static void main(String[] args) {
		String data = "samples/0.txt";
		String[] sentences = vn.hus.nlp.utils.UTF8FileUtility.getLines(data);
		
		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		for (String sentence : sentences) {
			try {
				StringBuffer result = new StringBuffer(1024);
				List<WordTag> list = tagger.tagText2(sentence);
				for (WordTag wordTag : list) {
					if(!wordTag.tag().equals("R")&&!wordTag.tag().equals("L")){
					result.append(wordTag.word());
					result.append(IConstants.DELIM);
					result.append(wordTag.tag());
					result.append(" ");
					}
				}
				System.out.println(result.toString().trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
	}
}
