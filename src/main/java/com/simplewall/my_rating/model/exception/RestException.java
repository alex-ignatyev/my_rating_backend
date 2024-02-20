package com.simplewall.my_rating.model.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class RestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    public RestException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}