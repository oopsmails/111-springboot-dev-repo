package com.oopsmails.springboot.mockbackend.filedownload.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
//@CrossOrigin
@Slf4j
public class StreamingResponseBodyController {
    private static final Logger logger = LoggerFactory.getLogger(StreamingResponseBodyController.class);

    @RequestMapping(value = "/downloadFile/{filetype}", method = {RequestMethod.GET, RequestMethod.POST})
    public StreamingResponseBody getSteamingFile(@PathVariable("filetype") String fileType,
                                                 @RequestParam("filename") String fileName,
                                                 HttpServletResponse response) throws IOException {

        logger.debug("fileName is {}", fileName);

        response.setContentType(getContentType(fileType)); //"application/pdf"
        response.setHeader("Content-Disposition", getContentDisposition(fileName));

//        response.setHeader("Access-Control-Allow-Origin", "*"); //ok, without WebMvcConfigurer in SpringBootBackendMockApplication

        InputStream inputStream = new FileInputStream(new File(getFileNameWithPath(fileType, fileName)));
        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
        };
    }

    private String getContentType(String fileType) {
        String result = "";

        if ("txt".equals(fileType)) {
            return MediaType.TEXT_PLAIN_VALUE;
        } else if ("pdf".equals(fileType)) {
            return MediaType.APPLICATION_PDF_VALUE;
        }
        return result;
    }

    private String getContentDisposition(String fileName) {
        return "attachment; filename=\"" + fileName + "\"";
    }

    private String getFileNameWithPath(String fileType, String fileName) {
//        return "C:\\Temp\\MockbackendDownloadTest\\" + fileType + "\\" + fileName;
//        return "C:\\Temp\\MockbackendDownloadTest\\" + "\\" + fileName + "." + fileType;
        return "C:\\Temp\\MockbackendDownloadTest\\" + "\\" + fileName;
    }

}
