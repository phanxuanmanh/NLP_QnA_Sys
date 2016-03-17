package main.demo.tokenizer;

import java.util.Set;

import main.demo.DBConnect.WordAccessor;

public class TestGetWord {
public static void main(String[] args) {
	 WordAccessor wordAccess= new WordAccessor();
	 Set<String> listDBWord = wordAccess.getListkeyWord();
	 for(String str: listDBWord){
		 System.out.println(str);
	 }
}
}
