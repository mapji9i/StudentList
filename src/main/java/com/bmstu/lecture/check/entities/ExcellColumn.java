package com.bmstu.lecture.check.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFSheet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class ExcellColumn implements Comparable {

    private EventSignature eventSignature;
    protected Object[] values;
    protected String[] studentsNames;


    public abstract void createColumn(HSSFSheet sheet, int columnId);

    public abstract void parseColumn(HSSFSheet sheet, int columnId);

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof ExcellColumn))
            throw new RuntimeException("Can't compare ExcellColumn.class with " + o.getClass().getSimpleName());

        return eventSignature.compareTo(((ExcellColumn) o).getEventSignature());
    }



}
