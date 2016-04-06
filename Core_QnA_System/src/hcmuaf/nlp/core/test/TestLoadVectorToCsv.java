package hcmuaf.nlp.core.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import hcmuaf.nlp.core.DBConnect.QnAAccessor;
import hcmuaf.nlp.core.DBConnect.WordAccessor;
import hcmuaf.nlp.core.controller.CsvDataWriter;
import hcmuaf.nlp.core.model.Keyword;

public class TestLoadVectorToCsv {
	public static void main(String[] args) throws IOException, SQLException {
		CsvDataWriter writer = new CsvDataWriter();
		ArrayList<Keyword> liskw = WordAccessor.getListkeyWord2();
		String[] header = new String[liskw.size() + 1];
		for (int i = 0; i < header.length - 1; i++) {
			header[i] = "Attr " + liskw.get(i).getId();
		}
		header[header.length - 1] = "Class";
		writer.writeDataToCsv("Testfile.csv", QnAAccessor.readQuestionVectorData(), header);
	}

}
