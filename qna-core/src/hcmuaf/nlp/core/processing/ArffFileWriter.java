/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.processing;

import hcmuaf.nlp.core.dao.KeyWordDao;
import hcmuaf.nlp.core.hibernateDao.impl.KeyWordDaoImpl;
import hcmuaf.nlp.core.model.Keyword;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 * The Class ArffFileWriter.
 */
public class ArffFileWriter {
	
	/**
	 * Convert csv to arff file.
	 *
	 * @param srcCsvFile the src csv file
	 * @param destArffFile the dest arff file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public  void convertCsvToArffFile(String srcCsvFile,
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

	/**
	 * Write data to file.
	 *
	 * @param fileName the file name
	 * @param listQuestionID the list question id
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	public void writeDataToFile(String fileName, int[] listQuestionID)
			throws IOException, SQLException {
		KeyWordDao keyWordDao = new KeyWordDaoImpl();
		CsvDataWriter writer = new CsvDataWriter();
		ArrayList<Keyword> liskw = keyWordDao.getListkeyWord2();
		String[] header = new String[liskw.size() + 1];
		for (int i = 0; i < header.length - 1; i++) {
			header[i] = "Attr " + liskw.get(i).getId();
		}
		header[header.length - 1] = "Class";
		writer.writePerLineToCSV(fileName + ".csv", listQuestionID, header);
		convertCsvToArffFile(fileName + ".csv", fileName + ".arff");
	}
}
