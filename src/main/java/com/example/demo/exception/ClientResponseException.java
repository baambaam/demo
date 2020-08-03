package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ClientResponseException extends Throwable {
    private final HttpStatus statusCode;

    public ClientResponseException(HttpStatus statusCode, String message) {
        super(message);

        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
