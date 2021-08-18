package com.zupacademy.kleysson.proposta.config.exceptions;

import org.springframework.http.HttpStatus;

public class ApiErroException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final String reason;
    private final String field;

    public ApiErroException(HttpStatus httpStatus, String reason, String field) {
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.field = field;
    }

    public ApiErroException(Integer statusCode, String reason, String field){
        this.httpStatus = HttpStatus.valueOf(statusCode);
        this.reason = reason;
        this.field = field;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getReason() {
        return reason;
    }

    public String getField() {
        return field;
    }
}
