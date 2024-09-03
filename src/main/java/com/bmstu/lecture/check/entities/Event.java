package com.bmstu.lecture.check.entities;


import com.bmstu.lecture.check.excellUtils.ColumnWriter;
import com.bmstu.lecture.check.excellUtils.ExcellService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class Event extends ExcellColumn {
    @Autowired
    ExcellService excellService;

    public Event(EventSignature eventSignature, Object[] values, String[] studentsNames) {
        super(eventSignature, values, studentsNames);
    }

    public Event(ExcellService excellService) {
        this.excellService = excellService;
    }

    @Override
    public void createColumn(HSSFSheet sheet, int columnId) {

        Cell cell = null;
        EventSignature eventSignature = getEventSignature();
        int rowId = -1;
        HSSFRow row = ColumnWriter.getOrCreateRow(sheet, ++rowId);
        cell = ColumnWriter.getOrCreateCell(row, columnId);
        cell.setCellValue(eventSignature.getMarksType().getFriendlyName());

        row = ColumnWriter.getOrCreateRow(sheet, ++rowId);
        cell = ColumnWriter.getOrCreateCell(row, columnId);

        if (eventSignature.getDate() != null) {
            CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            short format = createHelper.createDataFormat().getFormat("dd-mm-yyyy");
            cellStyle.setDataFormat(format);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(eventSignature.getDate());
        } else {
            cell.setCellValue(" ");
        }

        row = ColumnWriter.getOrCreateRow(sheet, ++rowId);
        cell = ColumnWriter.getOrCreateCell(row, columnId);
        ;
        cell.setCellValue(eventSignature.getName());

        for (Object value : values) {
            row = ColumnWriter.getOrCreateRow(sheet, ++rowId);
            cell = ColumnWriter.getOrCreateCell(row, columnId);
            if (eventSignature.getMarksType() == MarksType.BOOLEAN)
                cell.setCellValue((Boolean) value);
            else
                cell.setCellValue((Integer) value);
        }

    }

    @Override
    public void parseColumn(HSSFSheet sheet, int columnId) {
        EventSignature eventSignature = new EventSignature();


        int rowId = 0;

        HSSFCell cell = sheet.getRow(rowId++).getCell(columnId);
        ;
        eventSignature.setMarksType(MarksType.getEnumFromString(cell.getStringCellValue()));


        cell = sheet.getRow(rowId++).getCell(columnId);

        eventSignature.setDate(cell.getDateCellValue());

        cell = sheet.getRow(rowId++).getCell(columnId);
        ;
        eventSignature.setName(cell.getStringCellValue());
        this.setEventSignature(eventSignature);

        this.studentsNames = excellService.getStudentsNames();
        this.values = new Object[studentsNames.length];

        for (int i = 0; i < values.length; i++) {
            cell = sheet.getRow(rowId++).getCell(columnId);
            ;

            if (eventSignature.getMarksType() == MarksType.BOOLEAN)
                values[i] = cell.getBooleanCellValue();
            else
                values[i] = (int) cell.getNumericCellValue();
        }

    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Event)) return false;
        final Event other = (Event) o;
        if (!other.canEqual((Object) this)) return false;
        return this.getEventSignature().equals(other.getEventSignature());
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Event;
    }

    public int hashCode() {
        int result = 1;
        return result;
    }
}
