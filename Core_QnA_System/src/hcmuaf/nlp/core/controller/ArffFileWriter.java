package hcmuaf.nlp.core.controller;

import hcmuaf.nlp.core.DBConnect.WordAccessor;
import hcmuaf.nlp.core.model.Keyword;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class ArffFileWriter {
	public static void convertCsvToArffFile(String srcCsvFile,
			String destArffFile) throws IOException {
		File srcFile = new File(srcCsvFile);
		CSVLoader loader = new CSVLoader();
		loader.setSource(srcFile);
		Instances data = loader.getDataSet();

		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(destArffFile));
		saver.writeBatch();
		srcFile.delete();
	}

	public void writeDataToFile(String fileName, int[] listQuestionID)
			throws IOException, SQLException {
		CsvDataWriter writer = new CsvDataWriter();
		ArrayList<Keyword> liskw = WordAccessor.getListkeyWord2();
		String[] header = new String[liskw.size() + 1];
		for (int i = 0; i < header.length - 1; i++) {
			header[i] = "Attr " + liskw.get(i).getId();
		}
		header[header.length - 1] = "Class";
		writer.writePerLineToCSV(fileName + ".csv", listQuestionID, header);
		convertCsvToArffFile(fileName + ".csv", fileName + ".arff");
	}
}
