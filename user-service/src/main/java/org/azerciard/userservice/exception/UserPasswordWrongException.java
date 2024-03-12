package org.azerciard.userservice.exception;

public class UserPasswordWrongException extends RuntimeException {
    public UserPasswordWrongException(String message) {
        super(message);
    }

    public UserPasswordWrongException() {
        super("Password is wrong");
    }
}
