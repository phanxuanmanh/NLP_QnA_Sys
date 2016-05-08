package hcmuaf.nlp.core.test;

import java.io.IOException;

import hcmuaf.nlp.core.controller.ExcelDataAccessor;

public class TestReadQnA {
	public static void main(String[] args) {
		ExcelDataAccessor excelAcess = new ExcelDataAccessor();
		try {
			excelAcess.readExcelFile("QnA3.xlsx");
			excelAcess.readExcelFile("QnA4.xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
