/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.processing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

import hcmuaf.nlp.core.dao.QuestionVectorDao;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionVectorDaoImpl;
import hcmuaf.nlp.core.model.QuestionVectorCsv;

/**
 * The Class CsvDataWriter.
 */
public class CsvDataWriter {
	
	/**
	 * Write all data to csv.
	 *
	 * @param fileName the file name
	 * @param data the data
	 * @param header the header
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Write per line to csv.
	 *
	 * @param fileName the file name
	 * @param data the data
	 * @param header the header
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	public void writePerLineToCSV(String fileName, int[] data, String[] header)
			throws IOException, SQLException {
		QuestionVectorDao vectorDao = new QuestionVectorDaoImpl();
		BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(
				fileName)));
		CSVWriter writer = new CSVWriter(bfw);
		writer.writeNext(header);
		for (int qid : data) {
			QuestionVectorCsv vector = vectorDao.readQuestionVectorData(qid);
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
