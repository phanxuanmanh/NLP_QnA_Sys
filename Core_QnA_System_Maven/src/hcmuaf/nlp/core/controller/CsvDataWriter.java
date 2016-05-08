package hcmuaf.nlp.core.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

import hcmuaf.nlp.core.DBConnect.QnAAccessor;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

public class CsvDataWriter {
	public void writeAllDataToCsv(String fileName,
			ArrayList<QuestionVectorCsv> data, String[] header)
			throws IOException {
		BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(
				fileName)));
		CSVWriter writer = new CSVWriter(bfw);
		writer.writeNext(header);
		for (QuestionVectorCsv vector : data) {
			ArrayList<String> listWeight = vector.getWeightArr();
			listWeight.add("Class" + vector.getClassID());
			writer.writeNext(listWeight.toArray(new String[listWeight.size()]));

		}
		writer.flush();
		bfw.flush();
		writer.close();
		bfw.close();
	}

	public void writePerLineToCSV(String fileName, int[] data, String[] header)
			throws IOException, SQLException {
		BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(
				fileName)));
		CSVWriter writer = new CSVWriter(bfw);
		writer.writeNext(header);
		for (int qid : data) {
			QuestionVectorCsv vector = QnAAccessor.readQuestionVectorData(qid);
			if (vector != null) {
				ArrayList<String> listWeight = vector.getWeightArr();
				listWeight.add("Class" + vector.getClassID());
				writer.writeNext(listWeight.toArray(new String[listWeight
						.size()]));
				writer.flush();
				bfw.flush();
			}
		}
		writer.close();
		bfw.close();
	}
}
