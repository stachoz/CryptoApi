package com.example.cryptoapi.errors;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiException {
    private HttpStatus status;
    private String message;

    public ApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
