package com.bmstu.lecture.check.excellUtils;

import com.bmstu.lecture.check.entities.ExcellColumn;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor

public class ColumnWriter {
    @Autowired
    ExcellService excellService;


    public void WriteColumns() {

        HSSFWorkbook workbook = excellService.getExcellDataSource().getWorkBook();
        workbook.getAllNames();
        for (int i=workbook.getNumberOfSheets()-1;i>=0;i--)
            workbook.removeSheetAt(i);

        HSSFSheet sheet=workbook.createSheet("StudentsList");

        int colCount = -1;
        for (ExcellColumn column : excellService.getColumns()) {

            column.createColumn(sheet, ++colCount);
        }
        try (FileOutputStream outputStream = new FileOutputStream(excellService.getExcellDataSource().getExcellFile())) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static HSSFRow getOrCreateRow(HSSFSheet sheet, int rowId){
        HSSFRow row = sheet.getRow(rowId);
        return Objects.requireNonNullElseGet(row, () -> sheet.createRow(rowId));
    }
    public static HSSFCell getOrCreateCell(HSSFRow row, int cellId){
        HSSFCell cell = row.getCell(cellId);
        return Objects.requireNonNullElseGet(cell, () -> row.createCell(cellId));
    }
}
