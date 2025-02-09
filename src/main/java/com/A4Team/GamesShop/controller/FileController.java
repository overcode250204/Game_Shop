package com.A4Team.GamesShop.controller;

import com.A4Team.GamesShop.model.response.BaseResponse;
import com.A4Team.GamesShop.model.response.UserAuthResponse;
import com.A4Team.GamesShop.services.files.FileService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/avatar")
    public ResponseEntity<?> getUserAvatar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserAuthResponse)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponse.error(
                            HttpStatus.UNAUTHORIZED,
                            "User not authenticated",
                            List.of("Invalid token or session expired")
                    ));
        }

        UserAuthResponse user = (UserAuthResponse) authentication.getPrincipal();
        try {
            Resource file = fileService.getUserAvatar(user);
            String fileName = file.getFilename();
            MediaType mediaType = fileService.getFileMediaType(fileName);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BaseResponse.error(
                            HttpStatus.NOT_FOUND,
                            "Could not load avatar",
                            List.of(e.getMessage())
                    ));
        }
    }


}
