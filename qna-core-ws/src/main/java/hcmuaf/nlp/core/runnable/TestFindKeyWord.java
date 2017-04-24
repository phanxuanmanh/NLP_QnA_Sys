package hcmuaf.nlp.core.runnable;

import hcmuaf.nlp.core.processing.KeyWordFinder;

import java.io.File;


public class TestFindKeyWord {
	public static void main(String[] args) {
		File dir = new File("samples");
		for (File file : dir.listFiles()) {
			KeyWordFinder finder = new KeyWordFinder();
			finder.findWords(file.getPath());
		}

	}
}
