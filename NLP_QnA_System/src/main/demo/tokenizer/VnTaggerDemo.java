package main.demo.tokenizer;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;
public class VnTaggerDemo {
public static void main(String[] args) {
	String data = "samples/0.txt";
	String[] sentences = vn.hus.nlp.utils.UTF8FileUtility.getLines(data);
	
	VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
	for (String sentence : sentences) {
		try {
			System.out.println(tagger.tagText(sentence));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
}
