package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
//        1. get the current file name
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IOException("Original file name is null");
        }

//        2. Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.separator + fileName;
//        String filePath = path + fileName;

//        3. Check if path exist and create
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdirs();

//        4. Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

//        5. return file name
        return fileName;
    }


}
