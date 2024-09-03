package com.bmstu.lecture.check.entities;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EventSignature implements Comparable {

    private String name;
    private Date date;
    private MarksType marksType;



    @Override
    public int compareTo(Object o) {
        if (!(o instanceof EventSignature))
            throw new RuntimeException("Can't compare EventSignature.class with " + o.getClass().getSimpleName());
        else {
            // столбец ФИО должен быть первым всегда.
            // столбец Экзамен должен быть последним всегда.
            String[] compareNames = new String[]{"фио", "экзамен", "зачет", "курсовой проект", "доклад"};
            int[] inversionKeys = new int[]{-1, 1, 1, 1, 1};

            for (int i = 0; i < compareNames.length; i++) {
                String compareName = compareNames[i];
                int inversionKey = inversionKeys[i];
                if (this.getName().toLowerCase().equals(compareName)
                        && !((EventSignature) o).getName().toLowerCase().equals(compareName))
                    return 1 * inversionKey;
                else if (!this.name.toLowerCase().equals(compareName)
                        && ((EventSignature) o).getName().toLowerCase().equals(compareName))
                    return -1 * inversionKey;
                else if (this.name.toLowerCase().equals(compareName)
                        && ((EventSignature) o).getName().toLowerCase().equals(compareName))
                    return 0 * inversionKey;
            }


            Date compareDate = ((EventSignature) o).getDate();
            if (compareDate != null && this.date != null) {
                if (this.getName().toLowerCase().equals("лекция") && ((EventSignature) o).getName().toLowerCase().equals("лекция")) {
                    if (this.date.before(compareDate))
                        return -1;
                    else if (this.date.equals(compareDate))
                        return 0;
                    else if (this.date.after(compareDate))
                        return 1;
                } else if (this.getName().toLowerCase().equals("лекция") && !((EventSignature) o).getName().toLowerCase().equals("лекция")) {
                    return -1;
                } else if (!this.getName().toLowerCase().equals("лекция") && ((EventSignature) o).getName().toLowerCase().equals("лекция")) {
                    return 1;
                }

                if (this.date.before(compareDate))
                    return -1;
                else if (this.date.equals(compareDate))
                    return 0;
                else if (this.date.after(compareDate))
                    return 1;
            }
            return this.name.toLowerCase().compareTo(((EventSignature) o).getName().toLowerCase());

        }
    }




}