/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.processing;
import hcmuaf.nlp.core.dao.QnADao;
import hcmuaf.nlp.core.dao.QuestionTypeDao;
import hcmuaf.nlp.core.dto.QnAPair;
import hcmuaf.nlp.core.hibernateDao.impl.QnADaoImpl;
import hcmuaf.nlp.core.hibernateDao.impl.QuestionTypeDaoImpl;
import hcmuaf.nlp.core.model.QuestionType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The Class ExcelDataAccessor.
 */
public class ExcelDataAccessor {
	
	/** The type list. */
	private static ArrayList<QuestionType> typeList;
	
	/** The qna dao. */
	QnADao qnaDao = new QnADaoImpl();
	
	/** The question type dao. */
	QuestionTypeDao questionTypeDao = new QuestionTypeDaoImpl();
	
	/** The list qn a. */
	ArrayList<QnAPair> listQnA = new ArrayList<QnAPair>();
	
	/** The column. */
	int column = 0;

	/**
	 * Instantiates a new excel data accessor.
	 */
	public ExcelDataAccessor() {
		typeList = questionTypeDao.getQuestionTypeList();
	}

	/**
	 * Read excel file.
	 *
	 * @param excelFilePath the excel file path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void readExcelFile(String excelFilePath) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(
				excelFilePath));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			QnAPair pair = new QnAPair();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				column++;
				switch (column) {

				case 1:
					pair.setQuestion(cell.getStringCellValue());
					break;
				case 2:
					pair.setAnswer(cell.getStringCellValue());
					break;
				case 3:
					String typeName = cell.getStringCellValue();
					for (QuestionType type : typeList) {
						if (typeName.toUpperCase().equals(
								type.getTypeName().toUpperCase())) {
							pair.setTypeID(type.getTypeID());
						}
					}
					break;
				}
			}
			listQnA.add(pair);
			column = 0;
		}

		workbook.close();
		inputStream.close();
		for (QnAPair p : listQnA) {
			if (p.getQuestion() != null && p.getAnswer() != null
					&& p.getTypeID() > 0) {
				System.out.println(p.toString());
				qnaDao.insertQnAPair(p);
			}
		}

	}
}
