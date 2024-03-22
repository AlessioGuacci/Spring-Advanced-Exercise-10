package com.develhope.advanced12;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class DownloadController {
    @Autowired
    private DownloadService downloadService;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws Exception{
        return downloadService.upload(file);
    }

    @GetMapping("/download")
    public @ResponseBody byte[] download(@PathVariable String fileName, HttpServletResponse response) throws Exception{
        System.out.println("downloading " + fileName);
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension){
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("content-disposition", "attachment; fileName=\"" + fileName + "\"");
        return downloadService.download(fileName);
    }
}
