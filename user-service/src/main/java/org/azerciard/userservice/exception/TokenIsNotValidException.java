package org.azerciard.userservice.exception;

public class TokenIsNotValidException extends RuntimeException {
    public TokenIsNotValidException(String message) {
        super(message);
    }

    public TokenIsNotValidException() {
        super("Token is not valid!");
    }
}
