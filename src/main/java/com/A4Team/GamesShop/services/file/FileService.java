package com.A4Team.GamesShop.services.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void copyFile(MultipartFile multipartFile);
    Resource loadFile(String filename);
}
