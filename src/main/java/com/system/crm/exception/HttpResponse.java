package com.system.crm.exception;

public class HttpResponse {
    private Integer httpStatusCode;
    private String message;

    public HttpResponse(){}

    public HttpResponse(Integer httpStatusCode, String message) {
        this.message = message;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
