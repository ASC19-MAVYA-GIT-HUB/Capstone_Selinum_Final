package com.ajio.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtilities {

    public static Object[][] getData(String excelPath, String sheetName) throws InvalidFormatException, IOException {
        FileInputStream fis = new FileInputStream(new File(excelPath));
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];
        DataFormatter formatter = new DataFormatter();

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                data[i][j] = formatter.formatCellValue(row.getCell(j));
            }
        }

        workbook.close();
        fis.close();
        return data;
    }
}