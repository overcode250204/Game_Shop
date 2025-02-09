package com.A4Team.GamesShop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileUploadException extends RuntimeException {
    private final HttpStatus status;

    public FileUploadException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public FileUploadException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
