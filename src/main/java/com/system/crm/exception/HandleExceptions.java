package com.system.crm.exception;

import com.system.crm.exception.custom.CustomNotFoundException;
import com.system.crm.exception.custom.JwtAuthenticationException;
import com.system.crm.exception.custom.NotCorrectException;
import com.system.crm.exception.custom.SuccessResponse;
import com.system.crm.utils.ServiceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class HandleExceptions {

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse response = new HttpResponse();
        response.setHttpStatusCode(httpStatus.value());
        response.setMessage(message);
        return new ResponseEntity<>(response,httpStatus);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public Map<String, List<String>> handle(ConstraintViolationException exception) {
        Map<String, List<String>> constraints = ServiceUtils.errorsValidationConstraint(exception);
        return constraints;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpResponse> giftCertificateCreate(){
        return createHttpResponse(BAD_REQUEST,"Make sure you have correct input");
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<HttpResponse> giftCertificateCreate(CustomNotFoundException customNotFoundException){
        return createHttpResponse(HttpStatus.NOT_FOUND, customNotFoundException.getMessage());
    }


    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<HttpResponse> giftCertificateCreate(JwtAuthenticationException jwtAuthenticationException){
        return createHttpResponse(HttpStatus.UNAUTHORIZED, jwtAuthenticationException.getMessage());
    }


    @ExceptionHandler(SuccessResponse.class)
    public ResponseEntity<HttpResponse> giftCertificateCreate(SuccessResponse successResponse){
        return createHttpResponse(HttpStatus.OK, successResponse.getMessage());
    }

    @ExceptionHandler(NotCorrectException.class)
    public ResponseEntity<HttpResponse> giftCertificateCreate(NotCorrectException notCorrectException){
        return createHttpResponse(BAD_REQUEST, notCorrectException.getMessage());
    }


}
