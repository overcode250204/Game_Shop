package com.A4Team.GamesShop.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class BaseResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    private List<String> errors;

    public static <T> BaseResponse<T> success(T data, String message) {
        return new BaseResponse<>(HttpStatus.OK.value(), message, data, null);
    }

    public static BaseResponse<?> error(HttpStatus status, String message, List<String> errors){
        return new BaseResponse<>(status.value(), message, null, errors);
    }
}
