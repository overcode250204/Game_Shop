package com.A4Team.GamesShop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GoogleAuthenticationException extends RuntimeException{
    private final HttpStatus status;

    public GoogleAuthenticationException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public GoogleAuthenticationException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }
}
