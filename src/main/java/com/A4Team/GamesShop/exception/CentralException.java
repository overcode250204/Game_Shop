package com.A4Team.GamesShop.exception;

import com.A4Team.GamesShop.model.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class CentralException {

    @ExceptionHandler({LoginException.class})
    public ResponseEntity<BaseResponse<?>> handleLoginException(LoginException e) {
        BaseResponse<?> response = BaseResponse.error(e.getStatus(), e.getMessage(), List.of("Invalid credentials"));
        return ResponseEntity.status(e.getStatus()).body(response);
    }
    @ExceptionHandler({JwtExceptionCustom.class})
    public ResponseEntity<BaseResponse<?>> handleJwtException(JwtExceptionCustom e) {
        return ResponseEntity.status(e.getStatus())
                .body(BaseResponse.error(e.getStatus(), e.getMessage(), List.of("JWT error")));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<BaseResponse<?>> handleFileUploadException(FileUploadException e) {
        log.error("File upload error: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(BaseResponse.error(e.getStatus(), e.getMessage(), List.of("File upload failed")));
    }


    @ExceptionHandler({GoogleAuthenticationException.class})
    @ResponseStatus
    public ResponseEntity<BaseResponse<?>> handleGoogleAuthenticationException(GoogleAuthenticationException e) {
        return ResponseEntity.status(e.getStatus()).body(BaseResponse.error(e.getStatus(), e.getMessage(), List.of("Google authentication error")));
    }


}
