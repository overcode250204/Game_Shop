package com.A4Team.GamesShop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtExceptionCustom extends RuntimeException {
    private final HttpStatus status;

    public JwtExceptionCustom(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }

    public JwtExceptionCustom(String message){
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }

}
