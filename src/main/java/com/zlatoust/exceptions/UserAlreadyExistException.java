package com.zlatoust.exceptions;

public class UserAlreadyExistException extends ZlatoustException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
