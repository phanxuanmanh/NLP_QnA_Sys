package hcmuaf.nlp.core.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

import hcmuaf.nlp.core.model.QuestionVectorCsv;

public class CsvDataWriter {
	public void writeDataToCsv(String fileName, ArrayList<QuestionVectorCsv> data, String[] header) throws IOException {
		BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(fileName)));
		CSVWriter writer = new CSVWriter(bfw);
		writer.writeNext(header);
		for (QuestionVectorCsv vector : data) {
			ArrayList<String> listWeight = vector.getWeightArr();
			listWeight.add("Class" + vector.getClassID());
			/*
			 * String[] vectorArr = new String[listWeight.size()]; for(int i
			 * =0;i<vectorArr.length;i++){ vectorArr[i] = listWeight.get(i); }
			 */
			writer.writeNext(listWeight.toArray(new String[listWeight.size()]));

		}
		writer.flush();
		bfw.flush();
		writer.close();
		bfw.close();
	}
}
