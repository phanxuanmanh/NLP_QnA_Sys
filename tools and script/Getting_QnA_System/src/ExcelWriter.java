import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static ArrayList<QnA_Pair> listQnA;
	private int sheetNum;
	int rowCount = 1;

	public ExcelWriter() {
		sheetNum =1;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Sheet1");
		listQnA = new ArrayList<QnA_Pair>();
	}

	public void addQnA(QnA_Pair pair) {
		listQnA.add(pair);
	}

	public void writeToExcel(String fileName) throws FileNotFoundException, IOException {
		for (QnA_Pair p : listQnA) {
			if(rowCount>200){
				sheet = workbook.createSheet("Sheet"+ (++sheetNum));
				rowCount=0;
			}
			Row row = sheet.createRow(++rowCount);
			int columnCount = 0;
			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(p.getQuestion());
			Cell cell2 = row.createCell(columnCount);
			cell2.setCellValue(p.getAnswer());
		}
		try (FileOutputStream outputStream = new FileOutputStream(new File(fileName+".xlsx"))) {
			workbook.write(outputStream);
		}

	}
}
