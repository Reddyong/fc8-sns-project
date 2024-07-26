package com.fc8.snsproject.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User Not Founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post Not Founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),

    ALREADY_LIKED(HttpStatus.CONFLICT, "User Already Likes Post"),



    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),


    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Erro")
    ;

    private HttpStatus status;
    private String message;
}
