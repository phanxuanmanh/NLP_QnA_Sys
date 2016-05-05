package hcmuaf.nlp.core.wiki;

import hcmuaf.nlp.core.controller.WikiWordFinder;

public class TestWikiWord {
public static void main(String[] args) {
	long start = System.currentTimeMillis();
	WikiWordFinder finder = new WikiWordFinder();
	finder.conceptStatistic(22992321);
	long end = System.currentTimeMillis();
	System.out.println("time spent in ms : "+ (end-start));
}
}
