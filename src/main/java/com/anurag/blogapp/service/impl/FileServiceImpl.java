package com.anurag.blogapp.service.impl;


import com.anurag.blogapp.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // file name
        String fileName = file.getOriginalFilename();

        // generated random name
        String randomId = UUID.randomUUID().toString();
        String newFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));

        // full file path
        String filePath = path + File.separator + newFileName;

        // create folder
        File folder = new File(path);
        if(!folder.exists()) {
            folder.mkdir();
        }

        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) {
        return null;
    }
}
