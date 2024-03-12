package org.azerciard.userservice.exception;

public class UserAlreadyExistsWithPhoneNumberException extends RuntimeException{
    public UserAlreadyExistsWithPhoneNumberException() {
        super("User already exists with this phone number");
    }
}
