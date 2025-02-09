package com.A4Team.GamesShop.exception;

import com.A4Team.GamesShop.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
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

}
