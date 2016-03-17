package main.demo.tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestWorFinder {
	public static void main(String[] args) {
		File dir = new File("samples");
		for (File file : dir.listFiles()) {
			KeyWordFinder finder = new KeyWordFinder();
			finder.findWords(file.getPath());
		}

	}
}
