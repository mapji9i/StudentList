package com.bmstu.lecture.check.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum MarksType{
    BOOLEAN("Да/нет",Boolean.class),
    NUMBERIC("Число", Integer.class);
    @Getter
    private String friendlyName;
    private Class classObj;

    public static MarksType getEnumFromString(String friendlyName){
        for (MarksType marksType:MarksType.values()){
            if(marksType.friendlyName.equals(friendlyName)) return marksType;
        }
        throw  new RuntimeException("Enum value not found");
    }
    public Class getClassObj(){
            return this.classObj;
    }


}
