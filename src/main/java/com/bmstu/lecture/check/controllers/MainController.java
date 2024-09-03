package com.bmstu.lecture.check.controllers;


import com.bmstu.lecture.check.entities.EventSignature;
import com.bmstu.lecture.check.entities.ExcellColumn;
import com.bmstu.lecture.check.excellUtils.ColumnWriter;
import com.bmstu.lecture.check.excellUtils.ExcellService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bmstu.lecture.check.entities.Event;
import com.bmstu.lecture.check.entities.MarksType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
@Slf4j
@Controller
public class MainController {
    @Autowired
    ColumnWriter columnWriter;
    @Autowired
    private ExcellService excellService;
    //StudentsRepository students = new StudentsRepository("!Latest.xls");
    @GetMapping("/")
    private String root(){
        return "redirect:students-list";
    }
    @GetMapping("/students-list")
    private String studentsList(Model model){
        if(excellService.getColumns()==null) {
            model.addAttribute("files", null);
            return "load-excell-file";
        }
        model.addAttribute("events",excellService.getColumns());
        return "students-list";
    }

    @GetMapping("/create-event")
    private String createEvent(Model model) {
        model.addAttribute("marksTypes", MarksType.values());
        ArrayList<EventSignature> eventSignatures = new ArrayList<>();
        if (excellService.getColumns().size() != 0){
            Iterator iterator = excellService.getColumns().iterator();
            iterator.next();
            while (iterator.hasNext()) {
                eventSignatures.add(((ExcellColumn) iterator.next()).getEventSignature());
            }
        }
        model.addAttribute("exsistEventsSignatures", eventSignatures);
        return "create-event";
    }

    @GetMapping("/check-event")
    private String checkEvent(@RequestParam("name") String name, @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date, @RequestParam("marksType") String marksType, Model model) {
        ObjectMapper om = new ObjectMapper();//new ObjectMapper().writer().withDefaultPrettyPrinter();
        EventSignature eventSignature = new EventSignature(name, date,MarksType.getEnumFromString(marksType));

        String json = null;
        ExcellColumn event=excellService.getOrCreateEvent(eventSignature);
        try {
            json = om.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            return "students-list";
        }

        json=json.replace("\r\n","");
        model.addAttribute("JSONobj",json);

        return "check-event";
    }

    @PostMapping("/students-list")
    private ModelAndView CheckEventResult(@RequestBody Event event){
       excellService.getColumns().remove(event);
        excellService.addColumn(event);
        columnWriter.WriteColumns();

        return new ModelAndView("redirect:/students-list");
    }
    @PostMapping("/delete-event")
    private ModelAndView deleteEvent(@RequestBody Event event){
        excellService.getColumns().remove(event);
        columnWriter.WriteColumns();
        return new ModelAndView("redirect:/students-list");
    }
    @GetMapping("/load-excell-file")
    private String loadExcellFile(Model model){
        String directoryPath = excellService.getExcellDataSource()
                .getExcellFile()
                .getParent();
        File dir = new File(directoryPath);
        File[] filesInDir=dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().contains("Backup")) return true;
                return false;
            }
        });
        String[] filesNames=new String[filesInDir.length];
        for (int i=0; i<filesNames.length;i++)
            filesNames[i]=filesInDir[i].getName();
        model.addAttribute("files",filesNames);
        return "load-excell-file";
    }
    @GetMapping("/load-backup")
    private String loadBackup(@RequestParam("backupFileName") String backupFileName) {
        String directoryPath = excellService.getExcellDataSource()
                .getExcellFile()
                .getParent();
        File file = new File(directoryPath+"/"+backupFileName);
        String filePath=excellService.getExcellDataSource()
                .getExcellFile()
                .getPath();
        file.renameTo(new File(filePath));
        return "redirect:/students-list";
    }
//    @GetMapping(path = "/login")
//    private String loginForm(){
//        return "login";
//    }
    @PostMapping(path = "/load-excell-file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    private String readLoadedFile(@RequestParam("file") MultipartFile file){

        if (file!=null && file.getOriginalFilename().contains(".xls")) {

            String filePath=excellService.getExcellDataSource()
                    .getExcellFile()
                    .getPath();

            String directoryPath = excellService.getExcellDataSource()
                    .getExcellFile()
                    .getParent();

            String absoluteDirectoryPath=excellService
                    .getExcellDataSource()
                    .getExcellFile()
                    .getParentFile()
                    .getAbsolutePath();

            File backupFile=null;
            int i=1;
            do {
                String backupFileName = String.format( directoryPath + "/Backup_" + LocalDate.now() + "%s.xls","_"+i++);
                backupFile = new File(backupFileName);
            }while(backupFile.exists());

            File initialFile = new File(filePath);
            if(initialFile.exists())
                initialFile.renameTo(backupFile);

            if(file.getOriginalFilename().endsWith(".xlsx"))
                filePath+="x";
            try (OutputStream os = new FileOutputStream(filePath)) {
                os.write(file.getBytes());
                if(file.getOriginalFilename().contains(".xlsx")) {
                    log.info("Convert .xlsx to .xls");
                    String command=String.format("ssconvert %s %s",
                            absoluteDirectoryPath+"/!Latest.xlsx",
                            absoluteDirectoryPath+"/!Latest.xls");

                    Process process = Runtime.getRuntime()
                            .exec(command.split("\s"));

                    while(process.isAlive()){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    new File(filePath).delete();

                }
                excellService.init();


            }catch(IOException e){
                log.error(e.getMessage());
                if(initialFile.exists())
                    initialFile.renameTo(new File(filePath));
            }
        }
        return "redirect:/students-list";
    }

}
