package com.zlatoust.exceptions;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends ZlatoustException {
    public BadCredentialsException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
