package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	private static Workbook book;
	private static Sheet sheet;// always import ss intead of sl

	private static final String TEST_DATA_SHEET = "./src/main/resources/testdata/OpenCartTestData.xlsx";

	public static Object[][] getTestData(String sheetName) {

		System.out.println("Reading the data from sheet: " + sheetName);

		Object data[][] = null;

		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET);
			book = WorkbookFactory.create(ip); // interact via WorkbookFactory from ip
			sheet = book.getSheet(sheetName);// getSheet will ask the sheetName
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];// object array is initialized
																						// with 2-d
																						// blank array

			for (int i = 0; i < sheet.getLastRowNum(); i++)// for rows
			{

				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++)// since column values will be fixed, so goto
																			// 0th row and get the last cell number
																			// which will be equal to column size
				{

					data[i][j] = sheet.getRow(i + 1).getCell(j).toString();// i+1 because header is not needed in data,
																			// and toString because excel value will be
																			// converted to string value

				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
}
