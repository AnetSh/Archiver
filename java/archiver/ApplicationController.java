package archiver;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Anet on 7.05.2016.
 */
@Controller
@RequestMapping("/")
public class ApplicationController {

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    /**
     * Create archive only with one file. ( It should be finalized.
     * @param response
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addFileArch", method = RequestMethod.POST)
    @ResponseBody
    public FileSystemResource uploadFile(HttpServletResponse response,@RequestParam("file") MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile(String.valueOf(1 + (int)(Math.random() *10 )), ".zip");
        FileOutputStream fos = new FileOutputStream(tempFile.toFile());
        ZipOutputStream zos = new ZipOutputStream(fos);
        ZipEntry ze= new ZipEntry(file.getOriginalFilename());
        zos.putNextEntry(ze);
        zos.write(file.getBytes());
        zos.closeEntry();
        zos.close();
        response.setHeader("Content-Disposition", "attachment;filename=" + tempFile.toFile().getName());
        return new FileSystemResource(tempFile.toFile());
    }
}
