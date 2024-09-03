package com.bmstu.lecture.check.excellUtils;

import com.bmstu.lecture.check.entities.*;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

//@Component
@Service
@NoArgsConstructor
@Slf4j
public class ExcellService {
    @Getter
    private TreeSet<ExcellColumn> columns;
    private int[] firstCellIndex;
    @Autowired
    @Getter
    private ExcellDataSource excellDataSource;
    private String[] studentsNames;
    @PostConstruct
    public void init() {
        this.clear();
        excellDataSource.init();
        HSSFWorkbook workbook = excellDataSource.getWorkBook();
        if (workbook != null) {
            getStudentsNames();
            if (studentsNames!= null){
                columns = new TreeSet<>();
                int columnId = 0;
                ExcellColumn column = new StudentsNames(this);
                column.parseColumn(null,columnId++);
                this.addColumn(column);
                HSSFSheet sheet = workbook.getSheet("StudentsList");
                if (sheet != null) {
                    while (true) {
                        column = new Event(this);
                        try {
                            column.parseColumn(sheet, columnId++);
                            this.addColumn(column);
                        } catch (Exception e) {
                            e.getMessage();
                            break;
                        }
                    }
                }
        }
    }
    }

    public String[] getStudentsNames() {
        if (studentsNames == null) {
            List<String> result = new ArrayList<>();
            HSSFWorkbook wb = excellDataSource.getWorkBook();
            HSSFSheet sheet = (wb.getSheet("StudentsList") == null) ? (wb.getSheetAt(0)) : wb.getSheet("StudentsList");
            HSSFRow row;
            int[] indexes = getFirstCellIndex();
            if(indexes==null) return studentsNames;
                int cellIndex = indexes[1], rowIndex = indexes[0] + 1;
                while ((row = sheet.getRow(rowIndex)) != null) {
                    if (row.getCell(cellIndex) != null) {
                        result.add(row.getCell(cellIndex).getRichStringCellValue().getString());
                        rowIndex++;
                    } else break;

                }
                studentsNames=result.toArray(new String[result.size()]);

        }
        return studentsNames;
    }

private int[] getFirstCellIndex() {
    if (firstCellIndex==null) {
        HSSFWorkbook wb = excellDataSource.getWorkBook();
        HSSFSheet sheet = (wb.getSheet("StudentsList") == null) ? (wb.getSheetAt(0)) : wb.getSheet("StudentsList");
        Iterator rowIter = sheet.rowIterator();
        int cellIndex = 0;
        HSSFRow row = null;
        outerLoop:
        while (rowIter.hasNext()) {
            row = (HSSFRow) rowIter.next();
            Iterator cellIter = row.cellIterator();
            while (cellIter.hasNext()) {
                HSSFCell cell = (HSSFCell) cellIter.next();
                if (cell.getCellType()!= CellType.STRING)
                    continue;
                String str = cell.getRichStringCellValue().getString();
                if (str.contains("ФИО") ||  str.contains("Ф.И.О.") || str.contains("Фамилия")) {
                    cellIndex = cell.getColumnIndex();
                    firstCellIndex = new int[]{row.getRowNum(), cellIndex};
                    break outerLoop;
                }
                if (row.getRowNum()>100)
                    break outerLoop;
            }
        }
    }
    return firstCellIndex;
}

public void addColumn(ExcellColumn column) {
        if(columns!=null)
            this.columns.add(column);
}


public ExcellColumn getOrCreateEvent(EventSignature eventSignature) {
    String[] studentsNames = getStudentsNames();

    Object[] values = new Object[studentsNames.length];
    if (eventSignature.getMarksType() == MarksType.BOOLEAN)
        Arrays.fill(values, false);
    else
        Arrays.fill(values, 0);
    ExcellColumn event = new Event(eventSignature, values, studentsNames);

        Iterator<ExcellColumn> iterator = columns.iterator();
        while (iterator.hasNext()) {
            ExcellColumn potentialEvent = iterator.next();

            if (potentialEvent instanceof Event && event.equals((Event) potentialEvent)) {
                log.info("Find existed event"+potentialEvent);
                event = potentialEvent;
                break;
            }

    }
    return event;
}
    public void clear(){
        firstCellIndex = null;
        columns = null;
        studentsNames = null;
    }

}
