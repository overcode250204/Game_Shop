package com.A4Team.GamesShop.services.file;

import com.A4Team.GamesShop.exception.FileUploadException;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileServiceImp implements FileService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void copyFile(MultipartFile multipartFile){
        try {
            Path root = Paths.get(uploadPath);
            if(!Files.exists(root)){
                Files.createDirectory(root);
            }
            Path pathFile = root.resolve(multipartFile.getOriginalFilename());
            Files.copy(multipartFile.getInputStream(), pathFile, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path root = Paths.get(uploadPath).resolve(fileName);
            Resource resource = new UrlResource(root.toUri());
            if(resource.exists()){
                return resource;
            }
            throw new FileNotFoundException("File not found: " + fileName);
        }catch (Exception e){
            throw new FileUploadException("Could not load the file. Error: " + e.getMessage());
        }
    }

}
