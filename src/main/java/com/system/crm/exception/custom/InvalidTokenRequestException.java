package com.system.crm.exception.custom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTokenRequestException extends RuntimeException{
    private final String message;

    public InvalidTokenRequestException(String message, String errorMessage) {
        super(String.format("%s: [%s] ", message,errorMessage));
        this.message = message;
    }
}
