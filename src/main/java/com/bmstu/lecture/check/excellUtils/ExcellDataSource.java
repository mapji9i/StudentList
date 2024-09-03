package com.bmstu.lecture.check.excellUtils;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;

import java.io.*;


@Component
public class ExcellDataSource {

    private String filename= "excell_files/!Latest.xls";
    @Getter
    private HSSFWorkbook workBook;
    @Getter
    private File excellFile = new File(filename);
    @PostConstruct
    public void init() {
        File directory = new File(excellFile.getParent());
        if(!directory.exists())
            directory.mkdir();
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excellFile));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            this.workBook=wb;
        }
        catch (Exception e) {
            this.workBook=null;
        }
    }

    public void convertXLSXtoXLS(String filePath) {


    }
}
