package com.A4Team.GamesShop.services.files;

import com.A4Team.GamesShop.model.response.UserAuthResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveImage(MultipartFile multipartFile);
    String saveGameFile(MultipartFile multipartFile, Long userId, String gameName);
    Resource loadFile(String fileName, String folder);
    String generateFileUrl(String fileName, String folder);
    MediaType getFileMediaType(String fileName);
    Resource getUserAvatar(UserAuthResponse user);
}
