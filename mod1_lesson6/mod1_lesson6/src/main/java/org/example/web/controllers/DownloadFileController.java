package org.example.web.controllers;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping(value = "/files")
public class DownloadFileController {
    private static final String DIR_NAME = "external_uploads";
    private Logger logger = Logger.getLogger(BookShelfController.class);

    @GetMapping("/list")
    public String list(Model model) {
        File[] files = new File(new File(DIR_NAME).getAbsolutePath()).listFiles();
        model.addAttribute("filesList",
                files == null || files.length == 0 ? new ArrayList<File>() : Arrays.asList(files));
        return "files_page";
    }

    @GetMapping("/download/{fileName:.+}")
    public void downloader(HttpServletResponse response,
                           @PathVariable("fileName") String fileName) {
        try {
            File downloadFile = new File(new File(DIR_NAME).getAbsolutePath() + File.separator + fileName);
            FileInputStream is = new FileInputStream(downloadFile);
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            IOUtils.copy(is,response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            logger.error(String.format("Fail to download file: %s, %s", fileName, e.getMessage()));
        }
    }
}
