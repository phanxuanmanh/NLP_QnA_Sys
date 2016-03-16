package main.demo.tokenizer;
import java.util.List;
import java.util.Set;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;
import main.demo.DBConnect.WordAccessor;

public class KeyWordFinder {
private WordAccessor wordAccess;
public void findWords(String filePath){
	wordAccess= new WordAccessor();
	Set<String> listDBWord = wordAccess.getListkeyWord();
	String[] sentences = vn.hus.nlp.utils.UTF8FileUtility.getLines(filePath);
	
	VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
	for (String sentence : sentences) {
		try {
			StringBuffer result = new StringBuffer(1024);
			List<WordTag> list = tagger.tagText2(sentence);
			for (WordTag wordTag : list) {
				if(!wordTag.tag().equals("R")&&!wordTag.tag().equals("L")){
				if(!listDBWord.contains(wordTag.word().toUpperCase())){
					listDBWord.add(wordTag.word());
					wordAccess.addKeyWord(wordTag.word());
				}
				
				}
			}
			System.out.println(result.toString().trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
}
