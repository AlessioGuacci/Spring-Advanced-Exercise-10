package com.develhope.advanced12;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class DownloadService {
    @Value("{fileRepositoryFolder}")
    private String fileRepositoryFolder;

    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName= UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;
        File finalFolder = new File(fileRepositoryFolder);
        if(!finalFolder.exists()) throw new IOException("Final folder does not exist");
        if(!finalFolder.isDirectory()) throw new IOException("Final folder not in directory");

        File finalDestination= new File(fileRepositoryFolder + "\\" + completeFileName);
        if(finalDestination.exists()) throw  new IOException("file conflict");

        file.transferTo(finalDestination);
        return completeFileName;
    }

    public byte[] download(String fileName) throws IOException{
        File fileFromRepo = new File(fileRepositoryFolder + "\\" + fileName);
        if(!fileFromRepo.exists()) throw new IOException("error");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepo));
    }
}
