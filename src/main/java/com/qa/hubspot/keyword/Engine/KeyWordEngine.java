package com.qa.hubspot.keyword.Engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.keyword.Base.BasePage;
import com.qa.hubspot.keyword.KeyWords.KeyWordElementActions;

public class KeyWordEngine {
	public WebDriver driver;
	public Properties prop;
	public BasePage basePage;

	public KeyWordElementActions keyWordEleActions;
	public static Workbook book;
	public static Sheet sheet;
	public static ThreadLocal<Workbook> testBook = new ThreadLocal<Workbook>();
	public static ThreadLocal<Sheet> testSheet = new ThreadLocal<Sheet>();

	public final String TESTDATA_SHEET_PATH = "/Users/NaveenKhunteta/Documents/workspace/KeywordDrivenFW"
			+ "/src/main/java/com/qa/hubspot/keyword/Scenarios/hubspot_scenarios.xlsx";

	public void startExecution(String sheetName) {

		By locator;
		String locatorValue = null;
		String locatorName = null;
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
			testBook.set(book);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		testSheet.set(sheet);
		int k = 0;
		for (int i = 0; i < testSheet.get().getLastRowNum(); i++) {

			try {
				String locatorColValue = testSheet.get().getRow(i + 1).getCell(k + 1).toString().trim();
				if (!locatorColValue.equalsIgnoreCase("NA")) {
					locatorName = locatorColValue.split("=")[0].trim();
					locatorValue = locatorColValue.split("=")[1].trim();
				}
				String action = testSheet.get().getRow(i + 1).getCell(k + 2).toString().trim();
				String value = testSheet.get().getRow(i + 1).getCell(k + 3).toString().trim();

				switch (action) {

				case "open browser":
					basePage = new BasePage();
					prop = basePage.init_Properties();
					if (value.isEmpty() || value.equals("NA")) {
						driver = basePage.init_driver(prop.getProperty("browser"));
					} else {
						driver = basePage.init_driver(value);
					}
					keyWordEleActions = new KeyWordElementActions(driver);
					break;

				case "enter url":
					if (value.isEmpty() || value.equals("NA")) {
						keyWordEleActions.launchUrl(prop.getProperty("url"));
					} else {
						keyWordEleActions.launchUrl(value);
					}
					break;
				case "quit":
					keyWordEleActions.quitBrowser();
					break;
				default:
					System.out.println("No Action is defined");
					break;
				}

				switch (locatorName) {
				case "id":
					locator = keyWordEleActions.getId(locatorValue);
					if (action.equalsIgnoreCase("sendkeys")) {
						keyWordEleActions.sendKeys(locator, value);
					} else if (action.equalsIgnoreCase("click")) {
						keyWordEleActions.click(locator);
					}
					locatorName = null;
					break;
				case "linkText":
					locator = keyWordEleActions.getLinkText(locatorValue);
					keyWordEleActions.click(locator);
					locatorName = null;
					break;
				default:
					break;
				}
			} catch (Exception e) {
			}
		}

	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			Row row = null;
			Cell cell = null;

			FileInputStream fis = new FileInputStream(TESTDATA_SHEET_PATH);
			book = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = book.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = book.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			// CellStyle cs = workbook.createCellStyle();
			// cs.setWrapText(true);
			// cell.setCellStyle(cs);
			cell.setCellValue(data);

			FileOutputStream fileOut = new FileOutputStream(TESTDATA_SHEET_PATH);

			book.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
