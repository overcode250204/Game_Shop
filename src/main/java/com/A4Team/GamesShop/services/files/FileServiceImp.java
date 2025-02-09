package com.A4Team.GamesShop.services.files;

import com.A4Team.GamesShop.exception.FileUploadException;
import com.A4Team.GamesShop.model.response.UserAuthResponse;
import com.A4Team.GamesShop.utils.MediaTypeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {

    @Value("${upload.image-path}")
    private String imagePath;

    @Value("${upload.game-path}")
    private String gamePath;

    @Value("${server.url}")
    private String serverUrl;

    @Override
    public String saveImage(MultipartFile multipartFile) {
        return saveFile(multipartFile, imagePath);
    }

    @Override
    public String saveGameFile(MultipartFile multipartFile, Long userId, String gameName) {
        String userGamePath = gamePath + "/" + userId + "/" + gameName;
        return saveFile(multipartFile, userGamePath);
    }

    private String saveFile(MultipartFile multipartFile, String directoryPath) {
        try {
            if (multipartFile.isEmpty() || multipartFile.getOriginalFilename() == null) {
                throw new FileUploadException("File is empty or has no name.");
            }

            Path root = Paths.get(directoryPath);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            String originalFileName = multipartFile.getOriginalFilename();
            MediaType mediaType = MediaTypeFactory.getMediaType(originalFileName);

            // Kiểm tra loại file hợp lệ (chỉ nhận ảnh hoặc game file)
            if (!isValidFile(mediaType)) {
                throw new FileUploadException("Invalid file format. Only images and game files are allowed.");
            }

            String uploadedChecksum = getFileChecksum(multipartFile.getInputStream());

            // Kiểm tra file trùng nội dung
            for (Path existingFile : Files.newDirectoryStream(root)) {
                if (Files.isRegularFile(existingFile)) {
                    String existingChecksum = getFileChecksum(Files.newInputStream(existingFile));
                    if (existingChecksum.equals(uploadedChecksum)) {
                        return existingFile.getFileName().toString(); // Trả về tên file cũ
                    }
                }
            }

            // Nếu file nội dung khác, tạo tên mới bằng UUID
            String extension = getFileExtension(originalFileName);
            String newFileName = UUID.randomUUID() + "." + extension;
            Path filePath = root.resolve(newFileName);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (Exception e) {
            throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
        }
    }

    private boolean isValidFile(MediaType mediaType) {
        return mediaType.toString().startsWith("image/") || mediaType.toString().startsWith("application/");
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return (lastDotIndex > 0) ? fileName.substring(lastDotIndex + 1) : "";
    }

    private String getFileChecksum(InputStream inputStream) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }
        return HexFormat.of().formatHex(digest.digest());
    }

    @Override
    public Resource loadFile(String fileName, String folder) {
        try {
            Path filePath = Paths.get(folder).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
            throw new FileNotFoundException("File not found: " + fileName);
        } catch (Exception e) {
            throw new FileUploadException("Could not load the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource getUserAvatar(UserAuthResponse user) {
        try {
            if (user.getAvatar() == null || user.getAvatar().isEmpty()) {
                throw new FileNotFoundException("Avatar not found for user: " + user.getId());
            }

            String fileName = user.getAvatar().substring(user.getAvatar().lastIndexOf("/") + 1);
            Path filePath = Paths.get(imagePath).resolve(fileName);

            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("Avatar file not found: " + fileName);
            }

            return new UrlResource(filePath.toUri());
        } catch (Exception e) {
            throw new FileUploadException("Could not load user avatar. Error: " + e.getMessage());
        }
    }

    @Override
    public MediaType getFileMediaType(String fileName) {
        return MediaTypeFactory.getMediaType(fileName);
    }

    @Override
    public String generateFileUrl(String fileName, String folder) {
        return serverUrl + "/api/files/" + folder + "/" + fileName;
    }

}
