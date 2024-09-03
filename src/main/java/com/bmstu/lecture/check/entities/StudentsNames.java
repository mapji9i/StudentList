package com.bmstu.lecture.check.entities;


import com.bmstu.lecture.check.excellUtils.ColumnWriter;
import com.bmstu.lecture.check.excellUtils.ExcellService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor

public class StudentsNames extends ExcellColumn {


    @Autowired
    ExcellService excellService;

    @Override
    public void createColumn(HSSFSheet sheet, int columnId) {

        Cell cell=null;
        int rowId=-1;
        HSSFRow row= ColumnWriter.getOrCreateRow(sheet,++rowId);
        cell=ColumnWriter.getOrCreateCell(row,columnId);
        cell.setCellValue("Тип оценок");

        row= ColumnWriter.getOrCreateRow(sheet,++rowId);
        cell=ColumnWriter.getOrCreateCell(row,columnId);
        cell.setCellValue("Дата");

        row= ColumnWriter.getOrCreateRow(sheet,++rowId);
        cell=ColumnWriter.getOrCreateCell(row,columnId);
        cell.setCellValue(getEventSignature().getName());

        for (String studentName:studentsNames) {
            row= ColumnWriter.getOrCreateRow(sheet,++rowId);
            cell=ColumnWriter.getOrCreateCell(row,columnId);
            cell.setCellValue(studentName);
        }
    }

    @Override
    public void parseColumn(HSSFSheet sheet,int columnId) {
        this.setEventSignature(new EventSignature("ФИО", null,null));
        this.studentsNames=this.excellService.getStudentsNames();
        this.values=this.studentsNames;
    }
}
