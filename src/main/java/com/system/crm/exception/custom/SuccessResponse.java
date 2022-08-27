package com.system.crm.exception.custom;

public class SuccessResponse extends RuntimeException{
    public SuccessResponse(String message) {
        super(message);
    }
}
